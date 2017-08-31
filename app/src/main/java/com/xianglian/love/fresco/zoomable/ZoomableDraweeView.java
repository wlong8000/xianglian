/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.xianglian.love.fresco.zoomable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.xianglian.love.utils.Trace;

/**
 * DraweeView that has zoomable capabilities.
 * <p/>
 * Once the image loads, pinch-to-zoom and translation gestures are enabled.
 */
public class ZoomableDraweeView extends DraweeView<GenericDraweeHierarchy> implements
        ZoomableController.Listener {

    private static final String TAG = ZoomableDraweeView.class.getName();

    private static final float HUGE_IMAGE_SCALE_FACTOR_THRESHOLD = 1.1f;

    private final RectF mImageBounds = new RectF();

    private final RectF mViewBounds = new RectF();

    private boolean mSuccessFlag=false;

    private Handler mHandler=new Handler();

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
    private onSingleClickListener mOnSingleClickListener;
    private DraweeController mHugeImageController;
    private ZoomableController mZoomableController = DefaultZoomableController.newInstance();
    private GestureDetectorCompat mGestureDetectorCompat;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener=new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Trace.d(TAG,"onSingleTapConfirmed  "+mSuccessFlag);
            if (mOnSingleClickListener != null&&mSuccessFlag) {
                mOnSingleClickListener.onSingleClick(ZoomableDraweeView.this);
                return true;
            }
            return false;
        }
    };
    private final ControllerListener mControllerListener = new BaseControllerListener<Object>() {
        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSuccessFlag=true;
                    Trace.d(TAG,"onFinalImageSet  "+mSuccessFlag);
                }
            });
            ZoomableDraweeView.this.onFinalImageSet();
        }

        @Override
        public void onRelease(String id) {
            Trace.d(TAG,"onFailonReleaseure  "+mSuccessFlag);
            ZoomableDraweeView.this.onRelease();
            mSuccessFlag=false;
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            Trace.d(TAG,"onFailure  "+mSuccessFlag);
            mSuccessFlag=false;
            super.onFailure(id, throwable);
        }

    };

    public ZoomableDraweeView(Context context) {
        super(context);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mZoomableController.setListener(this);
        setOnClickListener(onClickListener);
        mGestureDetectorCompat =new GestureDetectorCompat(getContext(),mSimpleOnGestureListener);
    }

    public void setZoomableController(ZoomableController zoomableController) {
        Preconditions.checkNotNull(zoomableController);
        mZoomableController.setListener(null);
        mZoomableController = zoomableController;
        mZoomableController.setListener(this);
    }

    @Override
    public void setController(DraweeController controller) {
        setControllers(controller, null);
    }

    private void setControllersInternal(DraweeController controller,
            DraweeController hugeImageController) {
        removeControllerListener(getController());
        addControllerListener(controller);
        mHugeImageController = hugeImageController;
        super.setController(controller);
    }

    /**
     * Sets the controllers for the normal and huge image.
     * <p/>
     * IMPORTANT: in order to avoid a flicker when switching to the huge image,
     * the huge image controller should have the normal-image-uri set as its
     * low-res-uri.
     *
     * @param controller controller to be initially used
     * @param hugeImageController controller to be used after the client starts
     *            zooming-in
     */
    public void setControllers(DraweeController controller, DraweeController hugeImageController) {
        setControllersInternal(null, null);
        mZoomableController.setEnabled(false);
        setControllersInternal(controller, hugeImageController);
    }

    private void maybeSetHugeImageController() {
        if (mHugeImageController != null
                && mZoomableController.getScaleFactor() > HUGE_IMAGE_SCALE_FACTOR_THRESHOLD) {
            setControllersInternal(mHugeImageController, null);
        }
    }

    private void removeControllerListener(DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController)controller).removeControllerListener(mControllerListener);
        }
    }

    private void addControllerListener(DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController)controller).addControllerListener(mControllerListener);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(mZoomableController.getTransform());
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mGestureDetectorCompat.onTouchEvent(event)){
            return true;
        }
        if (mZoomableController.onTouchEvent(event)) {
            if (!mZoomableController.isOutside()) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Trace.v(TAG, "onLayout: view %x" + this.hashCode());
        super.onLayout(changed, left, top, right, bottom);
        updateZoomableControllerBounds();
    }

    private void onFinalImageSet() {
        Trace.v(TAG, "onFinalImageSet: view %x" + this.hashCode());
        if (!mZoomableController.isEnabled()) {
            updateZoomableControllerBounds();
            mZoomableController.setEnabled(true);
        }
    }

    private void onRelease() {
        Trace.v(TAG, "onRelease: view %x" + this.hashCode());
        mZoomableController.setEnabled(false);
    }

    @Override
    public void onTransformChanged(Matrix transform) {
        maybeSetHugeImageController();
        invalidate();
    }

    private void updateZoomableControllerBounds() {
        getHierarchy().getActualImageBounds(mImageBounds);
        mViewBounds.set(0, 0, getWidth(), getHeight());
        mZoomableController.setImageBounds(mImageBounds);
        mZoomableController.setViewBounds(mViewBounds);
    }

    public void setOnSingleClickListener(onSingleClickListener onSingleClickListener) {
        mOnSingleClickListener = onSingleClickListener;
    }

    public interface onSingleClickListener{
        void onSingleClick(View v);
    }

}
