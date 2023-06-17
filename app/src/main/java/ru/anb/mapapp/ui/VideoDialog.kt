package ru.anb.mapapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import ru.anb.mapapp.Constants
import ru.anb.mapapp.R
import ru.anb.mapapp.databinding.VideoDialogLayoutBinding

class VideoDialog: DialogFragment() {

    private var _binding: VideoDialogLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var videoUrl: String
    private lateinit var exoPlayer: ExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoUrl = arguments?.getString(Constants.VIDEO_URL_KEY)
            ?: throw IllegalArgumentException() // получение видео урл из бандл по ключу, если не приходит бросем исключение
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.video_dialog_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val styledPlayerView = view.findViewById<StyledPlayerView>(R.id.video)
         exoPlayer = ExoPlayer.Builder(requireContext()).build()
        styledPlayerView.player = exoPlayer
        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse( videoUrl)))
        exoPlayer.playWhenReady = true //
        exoPlayer.prepare()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
    }
}