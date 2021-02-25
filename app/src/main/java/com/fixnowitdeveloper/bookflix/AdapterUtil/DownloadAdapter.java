package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class DownloadAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int BOOK_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int EMPTY_VIEW = 4;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public DownloadAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {
            return BOOK_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof EmptyObject) {
            return EMPTY_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == BOOK_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_book_grid_item_layout, parent, false);
            viewHolder = new BookHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == EMPTY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

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
            EmptyObject emptyObject = (EmptyObject) wallpaperArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        } else if (holder instanceof BookHolder) {

            final DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final BookHolder featuredHolder = (BookHolder) holder;

            if (dataObject.isLongTap()) {
                featuredHolder.layoutDelete.setVisibility(View.VISIBLE);
            } else {
                featuredHolder.layoutDelete.setVisibility(View.GONE);
            }

            featuredHolder.txtBook.setText(dataObject.getTitle());
            featuredHolder.txtAuthor.setText(dataObject.getArtistName());

            featuredHolder.layoutBook.setTag(position);

            featuredHolder.layoutBook.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = (int) featuredHolder.layoutBook.getTag();
                    dataObject.setLongTap(true);
                    notifyItemChanged(pos);
                    return false;
                }
            });

            featuredHolder.layoutBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) featuredHolder.layoutBook.getTag();
                    select(pos);

                }
            });



            featuredHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) featuredHolder.layoutBook.getTag();
                    delete(pos);

                }
            });

            featuredHolder.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) featuredHolder.layoutBook.getTag();
                    dataObject.setLongTap(false);
                    notifyItemChanged(pos);

                }
            });


            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getOriginalUrl())
                    .into(featuredHolder.imageCover);


        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void select(int position);

    public abstract void delete(int position);

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

    protected class BookHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutBook;
        private ImageView imageCover;
        private TextView txtBook;
        private TextView txtAuthor;
        private RelativeLayout layoutDelete;
        private LinearLayout btnDelete;
        private LinearLayout btnClose;

        public BookHolder(View view) {
            super(view);

            layoutBook = (LinearLayout) view.findViewById(R.id.layout_book);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            txtAuthor = (TextView) view.findViewById(R.id.txt_author);
            layoutDelete = view.findViewById(R.id.layout_delete);
            btnDelete = view.findViewById(R.id.btn_delete);
            btnClose = view.findViewById(R.id.btn_close);

        }

    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }


}
