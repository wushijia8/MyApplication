package net.jaris.jaris_code.picasso.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.jaris.jaris_code.R;
import net.jaris.jaris_code.picasso.Utils;
import net.jaris.jaris_code.picasso.activity.PicassoTransfromationsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.MaskTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by Jaris on 2016/11/9.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class PicassoTransformationsAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public PicassoTransformationsAdapter(Context context, List<String> data){
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoler holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picasso_transformations,null);
            holder = new ViewHoler(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHoler) convertView.getTag();
        }

        //显示名称
        holder.name.setText("item"+(position+1));
        int integer = Integer.parseInt(mData.get(position));

        switch (integer) {

            case 1: {
                int width = Utils.dip2px(mContext, 133.33f);
                int height = Utils.dip2px(mContext, 126.33f);
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .resize(width, height)
                        .centerCrop()
                        .transform((new MaskTransformation(mContext, R.mipmap.ic_launcher)))
                        .into(holder.image);
                break;
            }
            case 2: {
                int width = Utils.dip2px(mContext, 150.0f);
                int height = Utils.dip2px(mContext, 100.0f);
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .resize(width, height)
                        .centerCrop()
                        .transform(new MaskTransformation(mContext, R.mipmap.ic_launcher))
                        .into(holder.image);
                break;
            }
            case 3:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.LEFT,
                                CropTransformation.GravityVertical.TOP))
                        .into(holder.image);
                break;
            case 4:
                Picasso.with(mContext).load(R.mipmap.ic_launcher)
                        // 300, 100, CropTransformation.GravityHorizontal.LEFT, CropTransformation.GravityVertical.CENTER))
                        .transform(new CropTransformation(300, 100)).into(holder.image);
                break;
            case 5:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.LEFT,
                                CropTransformation.GravityVertical.BOTTOM))
                        .into(holder.image);
                break;
            case 6:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.TOP))
                        .into(holder.image);
                break;
            case 7:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100))
                        .into(holder.image);
                break;
            case 8:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.BOTTOM))
                        .into(holder.image);
                break;
            case 9:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.RIGHT,
                                CropTransformation.GravityVertical.TOP))
                        .into(holder.image);
                break;
            case 10:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.RIGHT,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 11:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(300, 100, CropTransformation.GravityHorizontal.RIGHT,
                                CropTransformation.GravityVertical.BOTTOM))
                        .into(holder.image);
                break;
            case 12:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 16 / (float) 9,
                                CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 13:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 4 / (float) 3,
                                CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 14:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(3, CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 15:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(3, CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.TOP))
                        .into(holder.image);
                break;
            case 16:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation(1, CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 17:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 0.5, (float) 0.5,
                                CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 18:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 0.5, (float) 0.5,
                                CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.TOP))
                        .into(holder.image);
                break;
            case 19:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 0.5, (float) 0.5,
                                CropTransformation.GravityHorizontal.RIGHT,
                                CropTransformation.GravityVertical.BOTTOM))
                        .into(holder.image);
                break;
            case 20:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropTransformation((float) 0.5, 0, (float) 4 / (float) 3,
                                CropTransformation.GravityHorizontal.CENTER,
                                CropTransformation.GravityVertical.CENTER))
                        .into(holder.image);
                break;
            case 21:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropSquareTransformation())
                        .into(holder.image);
                break;
            case 22:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new CropCircleTransformation())
                        .into(holder.image);
                break;
            case 23:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new ColorFilterTransformation(Color.argb(80, 255, 0, 0)))
                        .into(holder.image);
                break;
            case 24:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new GrayscaleTransformation())
                        .into(holder.image);
                break;
            case 25:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new RoundedCornersTransformation(30, 0,
                                RoundedCornersTransformation.CornerType.BOTTOM_LEFT))
                        .into(holder.image);
                break;
            case 26:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new BlurTransformation(mContext, 25, 1))
                        .into(holder.image);
                break;
            case 27:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new ToonFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case 28:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new SepiaFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case 29:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new ContrastFilterTransformation(mContext, 2.0f))
                        .into(holder.image);
                break;
            case 30:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new InvertFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case 31:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new PixelationFilterTransformation(mContext, 20))
                        .into(holder.image);
                break;
            case 32:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new SketchFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case 33:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f)))
                        .into(holder.image);

                break;
            case 34:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new BrightnessFilterTransformation(mContext, 0.5f))
                        .into(holder.image);
                break;
            case 35:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new KuwaharaFilterTransformation(mContext, 25))
                        .into(holder.image);
                break;
            case 36:
                Picasso.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .transform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                                new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                        .into(holder.image);
                break;
        }

        return convertView;
    }

    class ViewHoler{

        @Bind(R.id.iv_picasso)
        ImageView image;

        @Bind(R.id.tv_picasso)
        TextView name;

        public ViewHoler(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
