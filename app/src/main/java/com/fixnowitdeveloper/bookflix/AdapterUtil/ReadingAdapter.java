package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.folioreader.model.ReadPositionImpl;
import com.google.gson.Gson;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class ReadingAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int BOOK_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int EMPTY_VIEW = 4;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();
    private Gson gson;
    private String TAG = ReadingAdapter.class.getName();


    public ReadingAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
        gson = new Gson();

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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_book_item_layout, parent, false);
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

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final BookHolder bookHolder = (BookHolder) holder;

            bookHolder.txtBook.setText(dataObject.getTitle());
            double percentage = 0.0;

            if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.EPUB)) {

                ReadPositionImpl readPosition = gson.fromJson(dataObject.getCurrentPage(), ReadPositionImpl.class);
                percentage = ((readPosition.getChapterIndex() + 0.0) / Double.parseDouble(dataObject.getBookPage())) * 100.0;

                Utility.Logger(TAG, "File Type : " + dataObject.getFileType() + " Percentage = " + percentage);

            } else if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.PDF)) {

                percentage = Double.parseDouble(dataObject.getCurrentPage()) / Double.parseDouble((dataObject.getBookPage())) * 100.0;

                Utility.Logger(TAG, "File Type : " + dataObject.getFileType() + " Percentage = " + percentage);

            }

            Utility.Logger(TAG, "Data = Total Pages : " + dataObject.getBookPage()
                    + " Current Page : " + dataObject.getCurrentPage() + " Book Type : " + dataObject.getFileType() +
                    " Percentage : " + percentage);

            bookHolder.txtReadingPercentage.setText((int) percentage + " %");
            bookHolder.progressBar.setProgress((int) percentage);

            bookHolder.layoutBook.setTag(position);
            bookHolder.imageCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) bookHolder.layoutBook.getTag();
                    selectWallpaper(pos);
                }
            });

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getCoverUrl())
                    .into(bookHolder.imageCover);


        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void selectWallpaper(int position);

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
        private ImageView imageCover;
        private TextView txtReadingPercentage;
        private TextView txtBook;
        private LinearLayout layoutBook;
        private ProgressBar progressBar;

        public BookHolder(View view) {
            super(view);

            txtBook = view.findViewById(R.id.txt_book);
            progressBar = view.findViewById(R.id.progress);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtReadingPercentage = view.findViewById(R.id.txt_reading_percentage);
            layoutBook = view.findViewById(R.id.layout_book);
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
