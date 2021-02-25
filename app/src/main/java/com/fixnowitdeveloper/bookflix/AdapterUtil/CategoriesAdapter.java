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
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CategoryObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.FeedHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ShelfObject;
import com.fixnowitdeveloper.bookflix.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class CategoriesAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int ROUND_CATEGORY_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int BAR_VIEW = 4;
    private int DATA_VIEW = 5;
    private int SQUARE_CATEGORY_VIEW = 6;
    private int CATEGORY_VIEW = 7;
    private int FEED_VIEW = 8;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();
    private int SHELF_VIEW = 9;


    public CategoriesAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof CategoryObject) {
            CategoryObject categoryObject = (CategoryObject) wallpaperArray.get(position);
            if (categoryObject.isRound())
                return ROUND_CATEGORY_VIEW;
            else
                return SQUARE_CATEGORY_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof BarObject) {
            return BAR_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {
            return DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof FeedHeaderObject) {
            return FEED_VIEW;
        } else if (wallpaperArray.get(position) instanceof ShelfObject)
            return SHELF_VIEW;

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == ROUND_CATEGORY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
            viewHolder = new CategoryHolder(view);

        } else if (viewType == SQUARE_CATEGORY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_shelf_item_layout, parent, false);
            viewHolder = new BookShelfAdapter(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
            viewHolder = new ArtistHolder(view);

        } else if (viewType == FEED_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_header_item_layout, parent, false);
            viewHolder = new FeedHeaderHolder(view);

        } else if (viewType == SHELF_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_item_layout, parent, false);
            viewHolder = new ShelfHolder(view);


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

        } else if (holder instanceof CategoryHolder) {

            CategoryObject categoryObject = (CategoryObject) wallpaperArray.get(position);
            final CategoryHolder categoryHolder = (CategoryHolder) holder;

            categoryHolder.layoutCategories.setTag(position);
            categoryHolder.layoutCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutCategories.getTag();
                    selectCategory(pos);
                }
            });

            categoryHolder.txtCategories.setText(categoryObject.getTitle());
            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + categoryObject.getPicture())
                    .into(categoryHolder.imageCategories);


        } else if (holder instanceof TopBarHolder) {

            TopBarHolder topBarHolder = (TopBarHolder) holder;
            BarObject barObject = (BarObject) wallpaperArray.get(position);

            topBarHolder.txtMenu.setText(barObject.getTitle());

        } else if (holder instanceof ArtistHolder) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final ArtistHolder categoryHolder = (ArtistHolder) holder;

            categoryHolder.layoutCategories.setTag(position);
            categoryHolder.layoutCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutCategories.getTag();
                    selectCategory(pos);
                }
            });

            categoryHolder.txtCategories.setText(dataObject.getTitle());
            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getOriginalUrl())
                    .into(categoryHolder.imageCategories);


        } else if (holder instanceof BookShelfAdapter) {

            CategoryObject dataObject = (CategoryObject) wallpaperArray.get(position);
            final BookShelfAdapter featuredHolder = (BookShelfAdapter) holder;

            featuredHolder.txtBook.setText(dataObject.getTitle());

            if (dataObject.isSelected()) {

                featuredHolder.layoutSelected.setVisibility(View.VISIBLE);

            } else {

                featuredHolder.layoutSelected.setVisibility(View.GONE);

            }

            featuredHolder.layoutBook.setTag(position);
            featuredHolder.layoutBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) featuredHolder.layoutBook.getTag();
                    selectCategory(pos);

                }
            });

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getPicture())
                    .into(featuredHolder.imageCover);


        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void selectCategory(int position);

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
        private ImageView imageCategories;
        private TextView txtCategories;

        public CategoryHolder(View view) {
            super(view);

            layoutCategories = (LinearLayout) view.findViewById(R.id.layout_categories);
            imageCategories = (ImageView) view.findViewById(R.id.image_categories);
            txtCategories = (TextView) view.findViewById(R.id.txt_categories);

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

    protected class ArtistHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutCategories;
        private ImageView imageCategories;
        private TextView txtCategories;

        public ArtistHolder(View view) {
            super(view);

            layoutCategories = (LinearLayout) view.findViewById(R.id.layout_categories);
            imageCategories = (ImageView) view.findViewById(R.id.image_categories);
            txtCategories = (TextView) view.findViewById(R.id.txt_categories);
        }

    }

    protected class BookShelfAdapter extends RecyclerView.ViewHolder {
        private LinearLayout layoutBook;
        private ImageView imageCover;
        private TextView txtBook;
        private RelativeLayout layoutSelected;

        public BookShelfAdapter(View view) {
            super(view);

            layoutBook = (LinearLayout) view.findViewById(R.id.layout_book);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            layoutSelected = view.findViewById(R.id.layout_selected);


        }
    }

    protected class FeedHeaderHolder extends RecyclerView.ViewHolder {


        public FeedHeaderHolder(View view) {
            super(view);


        }
    }

    protected class ShelfHolder extends RecyclerView.ViewHolder {


        public ShelfHolder(View view) {
            super(view);


        }
    }

}
