package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CategoryObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PlaylistObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class PlaylistAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int CATEGORY_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int BAR_VIEW = 4;
    private int DATA_VIEW = 5;
    private int PLAYLIST_VIEW = 5;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public PlaylistAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof CategoryObject) {
            return CATEGORY_VIEW;
        } else if (wallpaperArray.get(position) instanceof PlaylistObject) {
            return PLAYLIST_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof BarObject) {
            return BAR_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {
            return DATA_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == CATEGORY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
            viewHolder = new CategoryHolder(view);

        } else if (viewType == PLAYLIST_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item_layout, parent, false);
            viewHolder = new PlaylistHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == BAR_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_item_layout, parent, false);
            viewHolder = new TopBarHolder(view);

        } else if (viewType == DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_data_item_layout, parent, false);
            viewHolder = new RadioHolder(view);

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

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        } else if (holder instanceof PlaylistHolder) {

            PlaylistObject playlistObject = (PlaylistObject) wallpaperArray.get(position);
            final PlaylistHolder playlistHolder = (PlaylistHolder) holder;

            playlistHolder.imageOther.setTag(position);
            playlistHolder.imageOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = (int) playlistHolder.imageOther.getTag();

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, playlistHolder.imageOther);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    selectOther(pos);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();


                }
            });
            playlistHolder.layoutPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) playlistHolder.imageOther.getTag();
                    selectPlaylist(pos);
                }
            });

            playlistHolder.txtPlaylist.setText(playlistObject.getPlaylistName());


        } else if (holder instanceof TopBarHolder) {

            TopBarHolder topBarHolder = (TopBarHolder) holder;
            BarObject barObject = (BarObject) wallpaperArray.get(position);

            topBarHolder.txtMenu.setText(barObject.getTitle());

        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void selectOther(int position);

    public abstract void selectPlaylist(int position);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }
    }

    protected class CategoryHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutCategories;
        private ImageView imageFeature;
        private TextView txtCategory;

        public CategoryHolder(View view) {
            super(view);
            layoutCategories = (LinearLayout) view.findViewById(R.id.layout_category);
            imageFeature = (ImageView) view.findViewById(R.id.cover_radio);
            //txtCategory = (TextView) view.findViewById(R.id.txt_category);
        }

    }

    protected class PlaylistHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutPlaylist;
        private ImageView imageOther;
        private TextView txtPlaylist;

        public PlaylistHolder(View view) {
            super(view);

            layoutPlaylist = (LinearLayout) view.findViewById(R.id.layout_playlist);
            imageOther = (ImageView) view.findViewById(R.id.image_other);
            txtPlaylist = (TextView) view.findViewById(R.id.txt_playlist);
        }

    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    protected class TopBarHolder extends RecyclerView.ViewHolder {
        private TextView txtMenu;

        public TopBarHolder(View view) {
            super(view);

            txtMenu = view.findViewById(R.id.txt_menu);


        }
    }

    protected class RadioHolder extends RecyclerView.ViewHolder {
        private ImageView coverRadio;
        private TextView txtName;
        private TextView txtArtist;
        private LinearLayout layoutCategory;
        private RelativeLayout imageLocked;

        public RadioHolder(View view) {
            super(view);
            coverRadio = (ImageView) view.findViewById(R.id.cover_radio);
            layoutCategory = view.findViewById(R.id.layout_category);
            txtName = view.findViewById(R.id.txt_name);
            txtArtist = view.findViewById(R.id.txt_artist);
            //imageLocked = view.findViewById(R.id.image_locked);
        }

    }

}
