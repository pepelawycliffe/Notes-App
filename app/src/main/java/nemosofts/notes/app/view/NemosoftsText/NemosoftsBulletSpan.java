package nemosofts.notes.app.view.NemosoftsText;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;

public class NemosoftsBulletSpan extends BulletSpan {
    private static final int DEFAULT_COLOR = 0;
    private static final int DEFAULT_RADIUS = 3;
    private static final int DEFAULT_GAP_WIDTH = 2;
    private static Path bulletPath = null;

    private int bulletColor = DEFAULT_COLOR;
    private int bulletRadius = DEFAULT_RADIUS;
    private int bulletGapWidth = DEFAULT_GAP_WIDTH;

    public NemosoftsBulletSpan(int bulletColor, int bulletRadius, int bulletGapWidth) {
        this.bulletColor = bulletColor != 0 ? bulletColor : DEFAULT_COLOR;
        this.bulletRadius = bulletRadius != 0 ? bulletRadius : DEFAULT_RADIUS;
        this.bulletGapWidth = bulletGapWidth != 0 ? bulletGapWidth : DEFAULT_GAP_WIDTH;
    }

    public NemosoftsBulletSpan(Parcel src) {
        super(src);
        this.bulletColor = src.readInt();
        this.bulletRadius = src.readInt();
        this.bulletGapWidth = src.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(bulletColor);
        dest.writeInt(bulletRadius);
        dest.writeInt(bulletGapWidth);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return 2 * bulletRadius + bulletGapWidth;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();

            int oldColor = p.getColor();
            p.setColor(bulletColor);
            p.setStyle(Paint.Style.FILL);

            if (c.isHardwareAccelerated()) {
                if (bulletPath == null) {
                    bulletPath = new Path();
                    // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                    bulletPath.addCircle(0.0f, 0.0f, bulletRadius, Path.Direction.CW);
                }

                c.save();
                c.translate(x + dir * bulletRadius, (top + bottom) / 2.0f);
                c.drawPath(bulletPath, p);
                c.restore();
            } else {
                c.drawCircle(x + dir * bulletRadius, (top + bottom) / 2.0f, bulletRadius, p);
            }

            p.setColor(oldColor);
            p.setStyle(style);
        }
    }
}