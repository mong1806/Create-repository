package com.example.armeasure

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.SphereNode
import kotlin.math.sqrt

/**
 * AR 줄자: 화면을 두 번 탭하면 두 점 사이의 실제 거리(cm)를 표시한다.
 * 원리: ARCore가 카메라+센서로 공간을 인식 → 탭 위치를 실제 3D 좌표(m)로 변환 → 두 좌표의 직선거리 계산.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var sceneView: ARSceneView
    private lateinit var status: TextView
    private lateinit var distanceText: TextView

    private val anchors = mutableListOf<Anchor>()
    private val nodes = mutableListOf<AnchorNode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.sceneView)
        status = findViewById(R.id.status)
        distanceText = findViewById(R.id.distance)
        findViewById<Button>(R.id.reset).setOnClickListener { reset() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }

        sceneView.apply {
            // 바닥/벽 평면 인식 + 깊이 정보 사용(지원 기기)
            configureSession { session, config ->
                config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
                config.depthMode =
                    if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC))
                        Config.DepthMode.AUTOMATIC else Config.DepthMode.DISABLED
                config.focusMode = Config.FocusMode.AUTO
            }
            planeRenderer.isEnabled = true

            onSessionUpdated = { _, frame ->
                if (frame.camera.trackingState == TrackingState.TRACKING && anchors.isEmpty()) {
                    val hasPlane = frame.getUpdatedTrackables(com.google.ar.core.Plane::class.java)
                        .any { it.trackingState == TrackingState.TRACKING }
                    if (hasPlane) status.text = "첫 번째 지점을 탭하세요"
                }
            }

            setOnGestureListener(onSingleTapConfirmed = { e, _ ->
                if (anchors.size >= 2) return@setOnGestureListener
                val frame = session?.update() ?: return@setOnGestureListener
                val hit = frame.hitTest(e.x, e.y).firstOrNull() ?: run {
                    status.text = "인식된 면이 없습니다. 조금 더 비춰 주세요"
                    return@setOnGestureListener
                }
                addPoint(hit.createAnchor())
            })
        }
    }

    private fun addPoint(anchor: Anchor) {
        anchors += anchor
        val node = AnchorNode(sceneView.engine, anchor).apply {
            addChildNode(
                SphereNode(
                    engine = sceneView.engine,
                    radius = 0.012f,
                    materialInstance = sceneView.materialLoader.createColorInstance(
                        color = io.github.sceneview.math.colorOf(1f, 0.69f, 0.18f, 1f)
                    )
                )
            )
        }
        nodes += node
        sceneView.addChildNode(node)

        if (anchors.size == 1) {
            status.text = "두 번째 지점을 탭하세요"
        } else {
            val d = distanceMeters(anchors[0], anchors[1])
            distanceText.text = if (d >= 1f) "%.2f m".format(d) else "%.1f cm".format(d * 100)
            distanceText.visibility = TextView.VISIBLE
            status.text = "측정 완료"
        }
    }

    private fun distanceMeters(a: Anchor, b: Anchor): Float {
        val p = a.pose; val q = b.pose
        val dx = p.tx() - q.tx(); val dy = p.ty() - q.ty(); val dz = p.tz() - q.tz()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    private fun reset() {
        nodes.forEach { sceneView.removeChildNode(it); it.destroy() }
        nodes.clear()
        anchors.forEach { it.detach() }
        anchors.clear()
        distanceText.visibility = TextView.GONE
        status.text = "첫 번째 지점을 탭하세요"
    }
}
