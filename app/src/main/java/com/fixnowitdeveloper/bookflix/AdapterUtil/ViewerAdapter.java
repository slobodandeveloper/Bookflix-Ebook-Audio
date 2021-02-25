package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class ViewerAdapter extends RecyclerView.Adapter {
    private String TAG = ViewerAdapter.class.getName();
    private int NO_DATA_VIEW = 1;
    private int WALLPAPER_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private VideoView viewVideo;
    private Context context;
    private String dataType;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public ViewerAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    public ViewerAdapter(Context context, String dataType, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.dataType = dataType;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {
            return WALLPAPER_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == WALLPAPER_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lock_wallpaper_item_layout, parent, false);
            viewHolder = new LockScreenHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            //LookUpObject lookUpObject = (LookUpObject) wallpaperArray.get(position);
            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) wallpaperArray.get(position);


        } else if (holder instanceof LockScreenHolder) {

            final DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final LockScreenHolder lockScreenHolder = (LockScreenHolder) holder;


            if (dataObject.getPostType().equalsIgnoreCase("image")) {

                if (dataType.equalsIgnoreCase(Constant.DataType.DOWNLOAD)) {

                    GlideApp.with(context).load(dataObject.getOriginalUrl())
                            .into(lockScreenHolder.imageWallpaper);

                } else {

                    Utility.Logger(TAG, "Picture Url = " + Constant.ServerInformation.PICTURE_URL + dataObject.getOriginalUrl());
                    GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getOriginalUrl())
                            .into(lockScreenHolder.imageWallpaper);
                }

            } else if (dataObject.getPostType().equalsIgnoreCase("video")) {

                if (dataType.equalsIgnoreCase(Constant.DataType.DOWNLOAD)) {

                    GlideApp.with(context).load(dataObject.getCoverUrl())
                            .into(lockScreenHolder.imageWallpaper);

                } else {

                    GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getCoverUrl())
                            .into(lockScreenHolder.imageWallpaper);

                }

                lockScreenHolder.imagePlay.setVisibility(View.VISIBLE);
                lockScreenHolder.imagePlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String videoPath;
                        if (dataType.equalsIgnoreCase(Constant.DataType.DOWNLOAD)) {
                            videoPath = dataObject.getOriginalUrl();
                        } else {
                            videoPath = Constant.ServerInformation.VIDEO_URL + dataObject.getOriginalUrl();
                        }
                        onStartPlaying(lockScreenHolder.viewVideo, lockScreenHolder.imagePlay, dataType, videoPath);

                    }
                });


            }


        }


    }


    public abstract void onStartPlaying(VideoView videoView, ImageView imagePlay, String dataType, String videoPath);

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }

    protected class LockScreenHolder extends RecyclerView.ViewHolder {
        private ImageView imageWallpaper;
        private VideoView viewVideo;
        private ImageView imagePlay;

        public LockScreenHolder(View view) {
            super(view);

            imageWallpaper = (ImageView) view.findViewById(R.id.image_wallpaper);
            imagePlay = view.findViewById(R.id.image_play);
            //viewVideo = view.findViewById(R.id.view_video);

        }

    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {


        public ProgressHolder(View view) {
            super(view);


        }
    }

    public VideoView getViewVideo() {
        return viewVideo;
    }
}
