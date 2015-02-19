package com.app.pug.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class BitmapFunctions {
	
	private static final int TARGET_WIDTH = 0;
	private static final int TARGET_HEIGHT = 1;
	private static final int PIXEL_OFFSET = 5;
	
	public static final int UPLOAD_WIDTH = 1024;
	public static final int UPLOAD_HEIGHT = 768;

    /**
     * Open a Bitmap at a given File Path
     * @param imagePath The Image Path
     * @return Bitmap
     */
	public static Bitmap openImageFile(String imagePath) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 1;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		return bitmap;
	}

    /**
     * Scale an image to a specified Width and Height.
     * @param imagePath The Image Path
     * @return Bitmap
     */
	public static Bitmap getScaledUploadBitmap(String imagePath) {
		return getScaledBitmap(imagePath, new int[] {  UPLOAD_WIDTH, UPLOAD_HEIGHT });
	}

    /**
     * Crop an image to a circle defined by its Size
     * @param bitmap Bitmap
     * @return Bitmap The scaled image
     */
	public static Bitmap cropToCircle(Bitmap bitmap){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		final int color = 0xFF424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		paint.setAntiAlias(true);
		paint.setColor(color);
		canvas.drawCircle(width / 2, height / 2, width / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect,  rect, paint);
		return output;
	}

    /**
     * Scale an image to the size specified
     * @param bitmap The Bitmap
     * @param targetDimensions An array of Dimensions
     * @return Bitmap
     */
	public static Bitmap getScaledBitmap(Bitmap bitmap, int[] targetDimensions) {
		return createScaledBitmap(bitmap, targetDimensions[0], targetDimensions[1]);
	}

    /**
     * Scale an image from a File Path to the required Specified Target Dimensions
     * @param imagePath The Image Path
     * @param targetDimensions Target Dimensions
     * @return Bitmap
     */
	public static Bitmap getScaledBitmap(String imagePath, int[] targetDimensions) {
		
		Bitmap bitmap = null;
		BitmapFactory.Options options= new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		int originalWidth = 0, originalHeight = 0;
		
		File imageFile = new File(imagePath);
		if (!imageFile.exists()) return null;
		
		try {
			BitmapFactory.decodeFile(imagePath, options);
			originalWidth = options.outWidth;
			originalHeight = options.outHeight;
		} catch (Exception e) {}
		
		if (originalWidth <= 0 || originalHeight <= 0) return null;
		
		options.inJustDecodeBounds = false;
		
		int targetWidth = targetDimensions[0];
		int targetHeight = targetDimensions[1];
		
		int sampleSize = 1;
		
		if (originalWidth > originalHeight) {
			sampleSize = getSampleSize(originalHeight, originalWidth, targetWidth, targetHeight);
		} else {
			sampleSize = getSampleSize(originalWidth, originalHeight, targetWidth, targetHeight);
		}
		
		options.inSampleSize = sampleSize;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		
		if (bitmapWidth <= 0 || bitmapHeight <= 0) return null;
		
		int orientation = getOrientationFromExif(imagePath);
		int rotation = 0;
		
		if (orientation == 90) {
			rotation = 90;
		} else if (orientation == 270) {
			rotation = -90;
		} else if (orientation == 180) {
			rotation = 180;
		} else {
			rotation = 0;
		}
		
		Bitmap scaledBitmap = null;
		if (orientation != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(rotation);
			Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0 , bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			scaledBitmap = createScaledBitmap(rotatedBitmap, targetWidth, targetHeight);
		} else {
			scaledBitmap = createScaledBitmap(bitmap, targetWidth, targetHeight);
		}
		return scaledBitmap;
	}

    /**
     * Get an orientation of an image
     * @param imagePath The Image Path
     * @return Bitmap
     */
	private static int getOrientationFromExif(String imagePath) {
	    int orientation = -1;
	    try {
	        ExifInterface exif = new ExifInterface(imagePath);
	        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
 	        switch (exifOrientation) {
	            case ExifInterface.ORIENTATION_ROTATE_270: orientation = 270; break;
	            case ExifInterface.ORIENTATION_ROTATE_180: orientation = 180; break;
	            case ExifInterface.ORIENTATION_ROTATE_90: orientation = 90; break;
	            case ExifInterface.ORIENTATION_NORMAL: orientation = 0; break;
	            default: break;
	        }
	    } catch (IOException e) {}

	    return orientation;
	}

    /**
     * Create a scaled Bitmap from a source Bitmap to the target width and height
     * @param sourceBitmap Source Bitmap
     * @param targetWidth Target Image Width
     * @param targetHeight Target Height
     * @return Bitmap
     */
	private static Bitmap createScaledBitmap(Bitmap sourceBitmap, int targetWidth, int targetHeight) {
		Bitmap outputBitmap = sourceBitmap;
		int sourceWidth = sourceBitmap.getWidth();
		int sourceHeight = sourceBitmap.getHeight();
		int x = 0;
		int y = 0;
		int finalWidth = sourceWidth;
		int finalHeight = sourceHeight;
		if (fullyContained(sourceWidth, sourceHeight, targetWidth, targetHeight)) {
			if (isPortrait(sourceWidth, sourceHeight)) {
				if (isPortrait(targetWidth, targetHeight)) {
					int targetSide = getTargetSide(targetWidth, targetHeight, sourceWidth, sourceHeight);
					if (targetSide == TARGET_WIDTH) {
						finalWidth = sourceWidth;
						finalHeight = (int)(sourceWidth / (targetWidth * 1.0) * targetHeight);
						x = 0;
						y = (sourceHeight / 2) - (finalHeight / 2);
						Bitmap scaledBitmap = null;
						if (x == 0 && y == 0) {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
						} else {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
						}
						outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
					} else {
						finalWidth = (int)(sourceHeight / (targetHeight * 1.0) * targetWidth);
						finalHeight = sourceHeight;
						x = (sourceWidth / 2) - (finalWidth / 2);
						y = 0;
						Bitmap scaledBitmap = null;
						if (x == 0 && y == 0) {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
						} else {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
						}
						outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
					}
				} else {
					finalWidth = sourceWidth;
					finalHeight = (int)(sourceWidth / (targetWidth * 1.0) * targetHeight);
					x = 0;
					y = (sourceHeight / 2) - (finalHeight / 2);
					Bitmap scaledBitmap = null;
					if (x == 0 && y == 0) {
						scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
					} else {
						scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
					}
					outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
				}
			} else {
				if (isPortrait(targetWidth, targetHeight)) {
					finalWidth = (int)(sourceHeight / (targetHeight * 1.0) * targetWidth);
					finalHeight = sourceHeight;
					x = (sourceWidth / 2) - (finalWidth / 2);
					y = 0;
					Bitmap scaledBitmap = null;
					if (x == 0 && y == 0) {
						scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
					} else {
						scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
					}
					outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
				} else {
					int targetSide = getTargetSide(targetWidth, targetHeight, sourceWidth, sourceHeight);
					if (targetSide == TARGET_WIDTH) {
						finalWidth = sourceWidth;
						finalHeight = (int)(sourceWidth / (targetWidth * 1.0) * targetHeight);
						x = 0;
						y = (sourceHeight / 2) - (finalHeight / 2);
						Bitmap scaledBitmap = null;
						if (x == 0 && y == 0) {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
						} else {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
						}
						outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
					} else {
						finalWidth = (int)(sourceHeight / (targetHeight * 1.0) * targetWidth);
						finalHeight = sourceHeight;
						x = (sourceWidth / 2) - (finalWidth / 2);
						y = 0;
						Bitmap scaledBitmap = null;
						if (x == 0 && y == 0) {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, sourceWidth, sourceHeight);
						} else {
							scaledBitmap = Bitmap.createBitmap(sourceBitmap, x, y, finalWidth, finalHeight);
						}
						outputBitmap = Bitmap.createScaledBitmap(scaledBitmap, targetWidth, targetHeight, true);
					}
				}
			}
		} else if (widthContained(sourceWidth, sourceHeight, targetWidth, targetHeight)) {
			finalWidth = targetWidth + PIXEL_OFFSET;
			finalHeight = (int)(targetWidth / (sourceWidth * 1.0) * sourceHeight) + PIXEL_OFFSET;
			x = 0;
			y = (int)((finalHeight / 2.0) - (targetHeight / 2.0));
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(sourceBitmap, finalWidth, finalHeight, true);
			outputBitmap = Bitmap.createBitmap(scaledBitmap, x, y, targetWidth, targetHeight);
		} else if (heightContained(sourceWidth, sourceHeight, targetWidth, targetHeight)) {
			finalHeight = targetHeight + PIXEL_OFFSET;
			finalWidth = (int)(targetHeight / (sourceHeight * 1.0) * sourceWidth) + PIXEL_OFFSET;
			x = (int)((finalWidth / 2) - (targetWidth / 2));
			y = 0;
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(sourceBitmap, finalWidth, finalHeight, true);
			outputBitmap = Bitmap.createBitmap(scaledBitmap, x, y, targetWidth, targetHeight);
		} else {
			int targetSide = getTargetSide(sourceWidth, sourceHeight, targetWidth, targetHeight);
			if (targetSide == TARGET_WIDTH) {
				finalHeight = targetHeight + PIXEL_OFFSET;
				finalWidth = (int)(targetHeight / (sourceHeight * 1.0) * sourceWidth) + PIXEL_OFFSET;
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(sourceBitmap, finalWidth, finalHeight, true);
				x = (int)((finalWidth / 2) - (targetWidth / 2));
				y = 0;
				outputBitmap = Bitmap.createBitmap(scaledBitmap, x, y, targetWidth, targetHeight);
			} else {
				finalWidth = targetWidth + PIXEL_OFFSET;
				finalHeight = (int)(targetWidth / (sourceWidth * 1.0) * sourceHeight) + PIXEL_OFFSET;
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(sourceBitmap, finalWidth, finalHeight, true);
				x = 0;
				y = (int)((finalHeight / 2) - (targetHeight / 2));
				outputBitmap = Bitmap.createBitmap(scaledBitmap, x, y, targetWidth, targetHeight);
			}
		}
		return outputBitmap;
	}
	
	private static boolean fullyContained(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
		return (sourceWidth > targetWidth && sourceHeight > targetHeight);
	}
	
	private static boolean heightContained(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
		return (sourceWidth > targetWidth && sourceHeight < targetHeight);
	}
	
	private static boolean widthContained(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
		return (sourceWidth < targetWidth && sourceHeight > targetHeight);
	}
	
	private static boolean isPortrait(int width, int height) {
		return width < height;
	}
	
	private static int getTargetSide(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
		double width = sourceWidth;
		double height = sourceHeight;
		int widthCount = 0;
		int heightCount = 0;
		if (width > height) {
			double factor = width / height;
			while (height < targetHeight) {
				height++;
				heightCount++;
			}
			while (width < targetWidth) {
				width += factor;
				widthCount++;
			}
		} else {
			double factor = height / width;
			while (height < targetHeight) {
				height += factor;
				heightCount++;
			}
			while (width < targetWidth) {
				width++;
				widthCount++;
			}
		}
		return (widthCount < heightCount ? TARGET_WIDTH : TARGET_HEIGHT);
	}
	
	private static int getSampleSize(int originalWidth, int originalHeight, int targetWidth, int targetHeight) {
		int sampleSize = 1;
		while (originalWidth / sampleSize > targetWidth && originalHeight / sampleSize > targetHeight) {
			sampleSize *= 2;
		}
		return sampleSize / 2;
	}

    /**
     * Get the Device Width in Pixels
     * @param context Context
     * @return int
     */
    public static int getDeviceWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return  metrics.widthPixels;
    }

    /**
     * Returns the Device Height in pixels
     * @param context Context
     * @return int
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return  metrics.widthPixels;
    }

    public static int convertDpToPixel(float dp, Context context){
        float px = dp *  (context.getResources().getDisplayMetrics().densityDpi / 160f);
        return (int)px;
    }

    /**
     *  Get a Bitmap Image from a directory and resize it to the required size, then make it round
     * @param photoPath The target Image File path
     * @param targetWidth Target Image Width
     * @param targetHeight Target Image Height
     * @return Bitmap
     */
    public static Bitmap getRoundedShape(String photoPath, int targetWidth,  int targetHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                    targetHeight,Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();
            path.addCircle(((float) targetWidth - 1) / 2,
                    ((float) targetHeight - 1) / 2,
                    (Math.min(((float) targetWidth),
                            ((float) targetHeight)) / 2),
                    Path.Direction.CCW);

            canvas.clipPath(path);
            Bitmap sourceBitmap = bitmap;
            canvas.drawBitmap(sourceBitmap,
                    new Rect(0, 0, sourceBitmap.getWidth(),
                            sourceBitmap.getHeight()),
                    new Rect(0, 0, targetWidth,
                            targetHeight), null);
            return targetBitmap;
        }catch(NullPointerException exc) {
            Log.d("EXCEPTION", "Nullpointer", exc);
            return null;
        }
    }

    /**
     * Gets a Rounded Image of the Given Dimensions from the local Resources
     * @param imageId The Image Resource ID
     * @param context Context
     * @param targetWidth The Image Width
     * @param targetHeight The Image Height
     * @return Bitmap
     */
    public static Bitmap getRoundedShape(int imageId, Context context, int targetWidth,  int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    /**
     * Get a Rounded image Bitmap with same dimensions as the other one.
     * @param imageId
     * @param context
     * @return Bitmap
     */
    public static Bitmap getRoundedShape(int imageId, Context context) {
        int targetWidth = 160;
        int targetHeight = 160;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);

        targetWidth = bitmap.getWidth();
        targetHeight = bitmap.getHeight();

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    /**
     * Get the rounded Bitmap from the given bitmap
     * @param bm Bitmap
     * @param targetWidth Required size of Width
     * @param targetHeight Required size of height
     * @return Bitmap
     */
    public static Bitmap getRoundedBitmap(Bitmap bm, int targetWidth,  int targetHeight) {
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bm;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
}
