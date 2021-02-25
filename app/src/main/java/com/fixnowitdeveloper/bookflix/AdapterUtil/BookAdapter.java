package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.NativeAdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class BookAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int BOOK_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int NATIVE_VIEW = 4;
    private int BAR_VIEW = 5;
    private int BOOK_GRID_VIEW = 6;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public BookAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {

        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            if (dataObject.isList())
                return BOOK_VIEW;
            else
                return BOOK_GRID_VIEW;

        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof NativeAdObject) {
            return NATIVE_VIEW;
        } else if (wallpaperArray.get(position) instanceof BarObject) {
            return BAR_VIEW;
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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_layout, parent, false);
            viewHolder = new BookHolder(view);

        } else if (viewType == BOOK_GRID_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_grid_item_layout, parent, false);
            viewHolder = new FeaturedHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == NATIVE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_native_item_layout, parent, false);
            viewHolder = new NativeAdHolder(view);

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
            final BookHolder wallpaperHolder = (BookHolder) holder;

            wallpaperHolder.txtAuthor.setText(Utility.getStringFromRes(context, R.string.by) + " " + dataObject.getArtistName());
            wallpaperHolder.txtBook.setText(dataObject.getTitle());
            wallpaperHolder.ratingBook.setRating(Float.parseFloat(dataObject.getRating()));

            Document doc = Jsoup.parse(dataObject.getDescription());
            wallpaperHolder.txtDescription.setText(doc.text());

            wallpaperHolder.txtRating.setText(dataObject.getDownloads() + " " + Utility.getStringFromRes(context, R.string.download));
            wallpaperHolder.txtReview.setText(dataObject.getComments() + " " + Utility.getStringFromRes(context, R.string.review));

            wallpaperHolder.layoutBook.setTag(position);
            wallpaperHolder.layoutBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) wallpaperHolder.layoutBook.getTag();
                    select(false, pos);
                }
            });

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getOriginalUrl())
                    .into(wallpaperHolder.imageCover);


        } else if (holder instanceof TopBarHolder) {

            TopBarHolder topBarHolder = (TopBarHolder) holder;
            BarObject barObject = (BarObject) wallpaperArray.get(position);

            topBarHolder.txtMenu.setText(barObject.getTitle());

        } else if (holder instanceof FeaturedHolder) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final FeaturedHolder featuredHolder = (FeaturedHolder) holder;

            featuredHolder.txtBook.setText(dataObject.getTitle());
            featuredHolder.txtAuthor.setText(dataObject.getArtistName());
            featuredHolder.ratingBook.setRating(Float.parseFloat(dataObject.getRating()));

            featuredHolder.layoutBook.setTag(position);
            featuredHolder.layoutBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) featuredHolder.layoutBook.getTag();
                    select(false, pos);

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

    public abstract void select(boolean isLocked, int position);

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
        private RelativeLayout layoutBook;
        private TextView txtBook;
        private TextView txtAuthor;
        private AppCompatRatingBar ratingBook;
        private TextView txtRating;
        private TextView txtReview;
        private TextView txtDescription;
        private ImageView imageCover;

        public BookHolder(View view) {
            super(view);

            layoutBook = (RelativeLayout) view.findViewById(R.id.layout_book);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            txtAuthor = (TextView) view.findViewById(R.id.txt_author);
            ratingBook = (AppCompatRatingBar) view.findViewById(R.id.rating_book);
            txtRating = (TextView) view.findViewById(R.id.txt_rating);
            txtReview = (TextView) view.findViewById(R.id.txt_review);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    protected class NativeAdHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layoutAd;
        private NativeAd mNativeBannerAd;

        public NativeAdHolder(View view) {
            super(view);

            layoutAd = (LinearLayout) view.findViewById(R.id.layout_ad);
            List<String> testDevices = new ArrayList<>();
            testDevices.add(Constant.Credentials.ADMOB_TEST_DEVICE_ID);
            testDevices.add("HASHED_ID_2");
            AdSettings.addTestDevices(testDevices);
            mNativeBannerAd = new NativeAd(context, Constant.Credentials.FB_AD_PLACEMENT_ID);
            mNativeBannerAd.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Utility.Logger("Erro Ad", adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {

                    mNativeBannerAd.unregisterView();

                    // Add the Ad view into the ad container.

                    LayoutInflater inflater = LayoutInflater.from(context);
                    // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                    RelativeLayout adView = (RelativeLayout) inflater.inflate(R.layout.native_ad_item_layout, layoutAd, false);
                    //layoutAd.addView(adView);

                    // Add the AdChoices icon
                    LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
                    AdChoicesView adChoicesView = new AdChoicesView(context, mNativeBannerAd, true);
                    adChoicesContainer.addView(adChoicesView, 0);

                    // Create native UI using the ad metadata.
                    AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                    TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                    MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                    TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                    //TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                    TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
                    Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
                    nativeAdCallToAction.setTypeface(Font.ubuntu_regular_font(context));

                    // Set the Text.
                    nativeAdTitle.setText(mNativeBannerAd.getAdvertiserName());
                    ///nativeAdBody.setText(mNativeBannerAd.getAdBodyText());
                    nativeAdSocialContext.setText(mNativeBannerAd.getAdSocialContext());
                    nativeAdCallToAction.setVisibility(mNativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                    nativeAdCallToAction.setText(mNativeBannerAd.getAdCallToAction());
                    sponsoredLabel.setText(mNativeBannerAd.getSponsoredTranslation());

                    // Create a list of clickable views
                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(nativeAdTitle);
                    clickableViews.add(nativeAdCallToAction);

                    // Register the Title and CTA button to listen for clicks.
                    mNativeBannerAd.registerViewForInteraction(
                            adView,
                            nativeAdMedia,
                            nativeAdIcon,
                            clickableViews);
                    // Add the Native Banner Ad View to your ad container
                    layoutAd.addView(adView);

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            // Initiate a request to load an ad.
            mNativeBannerAd.loadAd();

        }
    }

    protected class TopBarHolder extends RecyclerView.ViewHolder {
        private TextView txtMenu;

        public TopBarHolder(View view) {
            super(view);

            txtMenu = view.findViewById(R.id.txt_menu);


        }
    }

    protected class FeaturedHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutBook;
        private ImageView imageCover;
        private TextView txtBook;
        private TextView txtAuthor;
        private RatingBar ratingBook;

        public FeaturedHolder(View view) {
            super(view);

            layoutBook = (LinearLayout) view.findViewById(R.id.layout_book);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            txtAuthor = (TextView) view.findViewById(R.id.txt_author);
            ratingBook = view.findViewById(R.id.rating_book);

        }
    }

}
