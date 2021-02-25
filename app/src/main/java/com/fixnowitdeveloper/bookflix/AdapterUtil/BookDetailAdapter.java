package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.BookDetailCallback;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.Artist;
import com.fixnowitdeveloper.bookflix.ObjectUtil.AdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.HomeObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.NativeAdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class BookDetailAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int RADIO_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int NATIVE_VIEW = 4;
    private int BAR_VIEW = 5;
    private int BOOK_HEADER_VIEW = 6;
    private int FOR_YOU_VIEW = 7;
    private int POPULAR_VIEW = 8;
    private int POPULAR_DATA_VIEW = 9;
    private int ADMOB_VIEW = 10;
    private int BOOK_REVIEW_VIEW = 11;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();
    private BookDetailCallback bookDetailCallback;
    private String TAG = Artist.class.getName();
    private String pictureUrl;
    private boolean isAddBanner = true;

    public BookDetailAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    public BookDetailAdapter(Context context, ArrayList<Object> wallpaperArray, BookDetailCallback bookDetailCallback) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
        this.bookDetailCallback = bookDetailCallback;


    }

    @Override
    public int getItemViewType(int position) {

        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof HomeObject) {

            HomeObject homeObject = (HomeObject) wallpaperArray.get(position);

            if (homeObject.getData_type() == Constant.DATA_TYPE.POPULAR) {
                return POPULAR_VIEW;
            } else if (homeObject.getData_type() == Constant.DATA_TYPE.COMMON) {
                return FOR_YOU_VIEW;
            }

        } else if (wallpaperArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.POPULAR) {
                return POPULAR_DATA_VIEW;
            } else if (dataObject.getDataType() == Constant.DATA_TYPE.REVIEW) {
                return BOOK_REVIEW_VIEW;
            }

        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof NativeAdObject) {
            return NATIVE_VIEW;
        } else if (wallpaperArray.get(position) instanceof BarObject) {
            return BAR_VIEW;
        } else if (wallpaperArray.get(position) instanceof BookHeaderObject) {
            return BOOK_HEADER_VIEW;
        } else if (wallpaperArray.get(position) instanceof AdObject) {
            return ADMOB_VIEW;
        }


        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == RADIO_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_layout, parent, false);
            viewHolder = new RadioHolder(view);

        } else if (viewType == POPULAR_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_layout, parent, false);
            viewHolder = new HomeHolder(view);

        } else if (viewType == POPULAR_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_popular_item_layout, parent, false);
            viewHolder = new PopularDataHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == NATIVE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_native_item_layout, parent, false);
            viewHolder = new NativeAdHolder(view);

        } else if (viewType == BAR_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_item_layout, parent, false);
            viewHolder = new TopBarHolder(view);

        } else if (viewType == BOOK_HEADER_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_header_item_layout, parent, false);
            viewHolder = new BookHeaderHolder(view);

        } else if (viewType == FOR_YOU_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_label_item_layout, parent, false);
            viewHolder = new ForYouHolder(view);

        } else if (viewType == BOOK_REVIEW_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
            viewHolder = new ReviewHolder(view);

        } else if (viewType == ADMOB_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_item_layout, parent, false);
            viewHolder = new AdHolder(view);

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


        } else if (holder instanceof RadioHolder) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final RadioHolder wallpaperHolder = (RadioHolder) holder;

            wallpaperHolder.txtAuthor.setText(Utility.getStringFromRes(context, R.string.by) + " " + dataObject.getArtistName());
            wallpaperHolder.txtBook.setText(dataObject.getTitle());
            wallpaperHolder.ratingBook.setRating(Float.parseFloat(dataObject.getLikes()));

            Document doc = Jsoup.parse(dataObject.getDescription());
            wallpaperHolder.txtDescription.setText(doc.text());
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


        } else if (holder instanceof BookHeaderHolder) {

            BookHeaderHolder artistHeaderHolder = (BookHeaderHolder) holder;
            BookHeaderObject bookHeaderObject = (BookHeaderObject) wallpaperArray.get(position);

            artistHeaderHolder.txtBook.setText(bookHeaderObject.getBookName());

            Document document = Jsoup.parse(bookHeaderObject.getBookDescription());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                artistHeaderHolder.txtDescription.setText(Html.fromHtml(document.toString(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                artistHeaderHolder.txtDescription.setText(Html.fromHtml(document.toString()));
            }

            artistHeaderHolder.ratingBook.setRating(Float.parseFloat(bookHeaderObject.getBookRating())/Float.parseFloat(bookHeaderObject.getBookReviewCount()));

            artistHeaderHolder.txtAuthor.setText(Utility.getStringFromRes(context, R.string.by) + " " + bookHeaderObject.getBookAuthorName());
            artistHeaderHolder.txtDownload.setText(bookHeaderObject.getBookDownloadCount() + " " + Utility.getStringFromRes(context, R.string.download));

            artistHeaderHolder.txtReview.setText(bookHeaderObject.getBookReviewCount() + " " + Utility.getStringFromRes(context, R.string.review));

            artistHeaderHolder.layoutRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookDetailCallback != null)
                        bookDetailCallback.readBook();
                }
            });

            artistHeaderHolder.layoutDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookDetailCallback != null)
                        bookDetailCallback.downloadBook();
                }
            });

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + bookHeaderObject.getBookImage())
                    .into(artistHeaderHolder.imageCover);

        } else if (holder instanceof HomeHolder) {

            HomeObject dataObject = (HomeObject) wallpaperArray.get(position);
            final HomeHolder homeHolder = (HomeHolder) holder;
            ArrayList<Object> list = new ArrayList<>();

            homeHolder.txtLabel.setText(dataObject.getLabel());
            homeHolder.txtTitle.setText(dataObject.getTitle());
            homeHolder.txtTitle.setTextSize(14);
            list.addAll(dataObject.getDataObjectArrayList());

            Utility.Logger(TAG, "List Size = " + list.size() + " Data Size = " + dataObject.getDataObjectArrayList().size());

            homeHolder.txtLabel.setTag(position);
            homeHolder.txtMore.setVisibility(View.GONE);

            homeHolder.artistAdapter = new BookDetailAdapter(context, list) {
                @Override
                public void select(boolean isLocked, int position) {
                    int pos = (int) homeHolder.txtLabel.getTag();
                    if (bookDetailCallback != null)
                        bookDetailCallback.onSelect(pos, position);
                }
            };
            homeHolder.recyclerViewRadio.setAdapter(homeHolder.artistAdapter);


        } else if (holder instanceof ForYouHolder) {

            HomeObject dataObject = (HomeObject) wallpaperArray.get(position);
            final ForYouHolder forYouHolder = (ForYouHolder) holder;

            forYouHolder.txtLabel.setText(dataObject.getLabel());
            forYouHolder.txtTitle.setText(dataObject.getTitle());
            forYouHolder.txtTitle.setTextSize(14);

            forYouHolder.txtMore.setVisibility(View.GONE);
            forYouHolder.layoutAddReview.setVisibility(View.VISIBLE);
            forYouHolder.layoutAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookDetailCallback != null) {
                        bookDetailCallback.addReview();
                    }
                }
            });


        } else if (holder instanceof PopularDataHolder) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final PopularDataHolder featuredHolder = (PopularDataHolder) holder;

            featuredHolder.txtBook.setText(dataObject.getTitle());
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


        } else if (holder instanceof ReviewHolder) {

            DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final ReviewHolder reviewHolder = (ReviewHolder) holder;

            reviewHolder.txtName.setText(dataObject.getReviewPersonName());
            reviewHolder.txtReview.setText(dataObject.getReviewPersonReview());
            reviewHolder.ratingReview.setRating(Float.parseFloat(dataObject.getReviewPersonRating()));

            Utility.Logger(TAG, "Login Type = " + dataObject.getLoginType());

            if (dataObject.getReviewPersonPicture().startsWith("http")) {

                if (dataObject.getLoginType().equalsIgnoreCase(Constant.LoginType.FACEBOOK_LOGIN))
                    pictureUrl = dataObject.getReviewPersonPicture() + Constant.ServerInformation.FACEBOOK_HIGH_PIXEL_URL;
                else
                    pictureUrl = dataObject.getReviewPersonPicture();


            } else {
                pictureUrl = Constant.ServerInformation.PICTURE_URL + dataObject.getReviewPersonPicture();
            }

            GlideApp.with(context).load(pictureUrl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(reviewHolder.imageUser);


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

    protected class RadioHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutBook;
        private TextView txtBook;
        private TextView txtAuthor;
        private AppCompatRatingBar ratingBook;
        private TextView txtRating;
        private TextView txtReview;
        private TextView txtDescription;
        private ImageView imageCover;

        public RadioHolder(View view) {
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

    protected class BookHeaderHolder extends RecyclerView.ViewHolder {
        private ImageView imageCover;
        private TextView txtBook;
        private TextView txtAuthor;
        private AppCompatRatingBar ratingBook;
        private TextView txtDownload;
        private TextView txtReview;
        private LinearLayout layoutRead;
        private LinearLayout layoutDownload;
        private TextView txtDescription;

        public BookHeaderHolder(View view) {
            super(view);

            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            txtAuthor = (TextView) view.findViewById(R.id.txt_author);
            ratingBook = (AppCompatRatingBar) view.findViewById(R.id.rating_book);
            txtDownload = (TextView) view.findViewById(R.id.txt_download);
            txtReview = (TextView) view.findViewById(R.id.txt_review);
            layoutRead = (LinearLayout) view.findViewById(R.id.layout_read);
            layoutDownload = (LinearLayout) view.findViewById(R.id.layout_download);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }

    }

    protected class HomeHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtLabel;
        private TextView txtMore;
        private GridLayoutManager gridLayoutManager;
        private RecyclerView recyclerViewRadio;
        private BookDetailAdapter artistAdapter;

        public HomeHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtLabel = (TextView) view.findViewById(R.id.txt_label);
            txtMore = view.findViewById(R.id.txt_more);

            gridLayoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRadio = (RecyclerView) view.findViewById(R.id.recycler_view_radio);
            recyclerViewRadio.setLayoutManager(gridLayoutManager);

            int spanCount = 3; // 3 columns
            int spacing = 15; // 50px
            boolean includeEdge = true;
            recyclerViewRadio.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        }
    }

    protected class ForYouHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtLabel;
        private TextView txtMore;
        private LinearLayout layoutAddReview;

        public ForYouHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtLabel = (TextView) view.findViewById(R.id.txt_label);
            txtMore = view.findViewById(R.id.txt_more);
            layoutAddReview = view.findViewById(R.id.layout_add_review);

        }
    }

    protected class PopularDataHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutBook;
        private ImageView imageCover;
        private TextView txtBook;
        private RatingBar ratingBook;

        public PopularDataHolder(View view) {
            super(view);

            layoutBook = (LinearLayout) view.findViewById(R.id.layout_book);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            txtBook = (TextView) view.findViewById(R.id.txt_book);
            ratingBook = view.findViewById(R.id.rating_book);

        }
    }

    protected class ReviewHolder extends RecyclerView.ViewHolder {
        private ImageView imageUser;
        private TextView txtName;
        private AppCompatRatingBar ratingReview;
        private TextView txtReview;

        public ReviewHolder(View view) {
            super(view);

            imageUser = (ImageView) view.findViewById(R.id.image_user);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            ratingReview = (AppCompatRatingBar) view.findViewById(R.id.rating_review);
            txtReview = (TextView) view.findViewById(R.id.txt_review);
        }
    }

    protected class AdHolder extends RecyclerView.ViewHolder {
        LinearLayout mAdView;

        public AdHolder(View view) {
            super(view);


            mAdView = view.findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);

            AdView adView = new AdView(context);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(Constant.Credentials.ADMOB_BANNER_ID);

            AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);

            GDPRChecker.Request request = GDPRChecker.getRequest();
            if (request == GDPRChecker.Request.NON_PERSONALIZED) {
                // load non Personalized ads
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);
            } // else do nothing , it will load PERSONALIZED ads
            if(isAddBanner){
                 adView.loadAd(adRequest.build());
                 mAdView.addView(adView);
                 isAddBanner = false;
            }



        }
    }


}
