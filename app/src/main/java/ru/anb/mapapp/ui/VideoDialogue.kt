package ru.anb.mapapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import ru.anb.mapapp.R
import ru.anb.mapapp.databinding.VideoDialogLayoutBinding

class VideoDialog(): DialogFragment() {

    private var _binding: VideoDialogLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        videoUrl = arguments?.getString(Constants.VIDEO_URL_KEY)?: throw IllegalArgumentException() // получение видео урл из бандл по ключу, если не приходит бросем исключение
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.video_dialog_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exoPlayer = ExoPlayer.Builder(view.context).build()
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"))

        binding.video.apply {
//            setVideoURI(Uri.parse("http://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8"))
//            seekTo(1)
//            requestFocus()
//            setMediaController(MediaController(this.context))
//            start()
            player = exoPlayer
            exoPlayer.playWhenReady = true
            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
        }
    }
}