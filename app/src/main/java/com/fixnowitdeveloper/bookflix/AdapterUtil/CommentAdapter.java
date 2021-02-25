package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> homeArrays = new ArrayList<>();
    private int EMPTY_VIEW = 1, COMMENT_VIEW = 2, INTERNET_VIEW = 3, PROGRESS_VIEW = 4;


    public CommentAdapter(Context context, ArrayList<Object> homeArrays) {
        this.context = context;
        this.homeArrays = homeArrays;
    }

    @Override
    public int getItemViewType(int position) {

        if (homeArrays.get(position) instanceof EmptyObject)
            return EMPTY_VIEW;
        else if (homeArrays.get(position) instanceof DataObject)
            return COMMENT_VIEW;
        else if (homeArrays.get(position) instanceof ProgressObject)
            return PROGRESS_VIEW;

        return EMPTY_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == EMPTY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == COMMENT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);
            viewHolder = new CommentHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof CommentHolder) {

            CommentHolder commentHolder = (CommentHolder) holder;
            DataObject comment = (DataObject) homeArrays.get(position);

            commentHolder.txtName.setText(comment.getFirstName() + " " + comment.getLastName());
            commentHolder.txtDescription.setText(comment.getComments());

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + comment.getPicture())
                    .into(commentHolder.imageProfile);


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) homeArrays.get(position);

            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());
            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());


        } else if (holder instanceof ProgressHolder) {

        }


    }

    @Override
    public int getItemCount() {
        return homeArrays.size();
    }

    protected class CommentHolder extends RecyclerView.ViewHolder {
        private CardView cardviewComment;
        private RoundedImageView imageProfile;
        private TextView txtName;
        private TextView txtDate;
        private TextView txtDescription;

        public CommentHolder(View view) {
            super(view);

            cardviewComment = (CardView) view.findViewById(R.id.cardview_comment);
            imageProfile = (RoundedImageView) view.findViewById(R.id.image_profile);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            //txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final TextView txtDescription;
        private ImageView imageIcon;

        public EmptyHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            imageIcon = view.findViewById(R.id.image_icon);


        }
    }


    protected class ProgressHolder extends RecyclerView.ViewHolder {
        GeometricProgressView geometricProgressView;

        public ProgressHolder(View view) {
            super(view);

            geometricProgressView = (GeometricProgressView) view.findViewById(R.id.progressView);


        }
    }

}
