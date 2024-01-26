package 코틀린디자인패턴.구조패턴
// Target Interface
interface MediaPlayer {
    fun play(mediaType: String, fileName: String)
}

// Concrete implementation of MediaPlayer for playing audio
class AudioPlayer : MediaPlayer {
    override fun play(mediaType: String, fileName: String) {
        if (mediaType.equals("audio", ignoreCase = true)) {
            println("Playing audio file: $fileName")
        } else {
            println("Invalid media. $mediaType format not supported by AudioPlayer")
        }
    }
}

// Adaptee
class VideoPlayer {
    fun playVideo(fileName: String) {
        println("Playing video file: $fileName")
    }
}

// Adapter
class VideoPlayerAdapter : MediaPlayer {
    private val videoPlayer = VideoPlayer()

    override fun play(mediaType: String, fileName: String) {
        if (mediaType.equals("video", ignoreCase = true)) {
            videoPlayer.playVideo(fileName)
        } else {
            println("Invalid media. $mediaType format not supported by VideoPlayerAdapter")
        }
    }
}

// Client Code
fun main() {
    val audioPlayer = AudioPlayer()
    val videoAdapter = VideoPlayerAdapter()

    audioPlayer.play("audio", "beyond_the_horizon.mp3")
    videoAdapter.play("video", "mind_meadows.mp4")
}
