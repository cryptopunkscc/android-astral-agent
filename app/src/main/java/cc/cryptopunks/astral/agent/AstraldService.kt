package cc.cryptopunks.astral.agent

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class AstraldService : Service(), CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    private val tag = ASTRAL + "Service"

    override fun onCreate() {
        loadAstralConfig()
        startForegroundNotification()

        launch {
            Log.d(tag, "Starting astral service")
            startAstral()
        }

        launch {
            astralStatus.filter { status ->
                status == AstralStatus.Started
            }.collect {
                jsAppsManager.startServices()
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(tag, "On low memory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(tag, "On trim memory")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(tag, "On task removed")
    }

    override fun onDestroy() {
        stopForeground(true)
        if (astralStatus.value != AstralStatus.Stopped) {
            Log.d(tag, "Destroying astral service")
            stopAstral()
        }
        cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onBind(intent: Intent) = null
}

fun Context.startAstralService() {
    val intent = Intent(this, AstraldService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
