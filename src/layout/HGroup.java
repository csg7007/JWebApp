package layout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * 水平布局
 * 
 * @author caishengguo
 * 
 */
public class HGroup {
	/** 容器组件 */
	private Container _compParent;
	/** 子组件数组 */
	private List<Component> listSonComp;
	/** 最后一个子组件 */
	private Component lastComponet;

	/** 垂直对齐方式：默认自顶向下 */
	private int verticalAlign = VERTICAL_TOP;
	/** 水平对齐方式：默认自左向右 */
	private int horizontalAlign = HORIZONTAL_LEFT;

	/** 水平间距 */
	private int hGap = HORIZONTAL_GAP;
	/** 垂直间距 */
	private int vGap = VERTICAL_GAP;

	/** 是否水平居中：默认为不居中 */
	private boolean isHorizontalCenter = false;
	/** 是否垂直居中：默认为不居中 */
	private boolean isVerticalCenter = false;

	/** 水平距离父组件的边距 */
	private int hParentGap = HORIZONTAL_GAP;
	/** 垂直距离父组件的边距 */
	private int vParentGap = VERTICAL_GAP;

	/** 水平间距默认值 */
	public static final int HORIZONTAL_GAP = 5;
	/** 垂直间距默认值 */
	public static final int VERTICAL_GAP = 5;
	/** 垂直对齐方式：自顶向下 */
	public static final int VERTICAL_TOP = 0;
	/** 垂直对齐方式：自底向上 */
	public static final int VERTICAL_BOTTOM = 1;

	/** 水平对齐方式：自左向右 */
	public static final int HORIZONTAL_LEFT = 0;
	/** 水平对齐方式：自右向左 */
	public static final int HORIZONTAL_RIGHT = 1;

	public HGroup(Container compParent) {
		_compParent = compParent;
		_compParent.setLayout(null);
	}

	public void add(Component comp) {
		if (comp != null) {
			listSonComp = new ArrayList<Component>();
			listSonComp.add(comp);
			// 由于是居中显示，则必须重新设置所有组件的位置
			if (isHorizontalCenter || isVerticalCenter) {
				initHVParentGap();
				// 布局第一个组件
				initLastComponent(listSonComp.get(0));
				// 布局其它的组件
				for (int i = 1; i < listSonComp.size(); i++) {
					hvLayout(listSonComp.get(i));
				}
			} else {
				// 如果不是居中显示，则直接布局该组件
				if (lastComponet == null) {
					initLastComponent(comp);
				}
				hvLayout(comp);
			}
		}
	}

	/**
	 * 对于当前组件布局 开始布局 首先是水平布局，然后设置垂直布局
	 */
	private void hvLayout(Component comp) {
		int x = 0;
		switch (horizontalAlign) {
		case HORIZONTAL_LEFT:
			x = hGap + lastComponet.getX() + lastComponet.getWidth();
			break;
		case HORIZONTAL_RIGHT:
			x = lastComponet.getX() - hGap - comp.getWidth();
			break;
		}
		comp.setBounds(x, comp.getY(),comp.getWidth(),comp.getHeight());
		lastComponet=comp;
		_compParent.add(comp);
		_compParent.validate();
	}

	private void initHVParentGap() {
		if (isHorizontalCenter) {
			initHParentGap();
		}
		if (isVerticalCenter) {
			initVParentGap();
		}
	}

	/**
	 * 水平居中组件
	 * 
	 * 当所有组件的宽度小于父容器的宽度时，则运用布局 否则自左向右或者自右向左布局
	 */
	private void initHParentGap() {
		int compTotalWidth = 0;
		hParentGap = 0;
		for (Component comp : listSonComp) {
			compTotalWidth += comp.getWidth();
		}
		compTotalWidth += (listSonComp.size() - 1) * hGap;
		if (compTotalWidth < _compParent.getWidth()) {
			hParentGap = (_compParent.getWidth() - compTotalWidth) / 2;
		}
	}

	private void initVParentGap() {
		int compTotalHeight = 0;
		vParentGap = 0;
		for (Component comp : listSonComp) {
			compTotalHeight += comp.getHeight();
		}
		compTotalHeight += (listSonComp.size() - 1) * VERTICAL_GAP;
		if (compTotalHeight < _compParent.getHeight()) {
			vParentGap = (_compParent.getHeight() - compTotalHeight) / 2;
		}
	}

	/**
	 * 对第一个元素进行布局
	 * 
	 * @param comp
	 */
	private void initLastComponent(Component comp) {
		lastComponet = comp;
		int x = lastComponet.getX();
		if (horizontalAlign == HORIZONTAL_LEFT) {
			x = hParentGap;
		} else if (horizontalAlign == HORIZONTAL_RIGHT) {
			x = _compParent.getWidth() - lastComponet.getWidth() - hParentGap;
		}
		lastComponet.setBounds(x, lastComponet.getY(),lastComponet.getWidth(),lastComponet.getHeight());
		_compParent.add(lastComponet);
		_compParent.validate();
	}

	public int getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public int getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(int horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public int gethGap() {
		return hGap;
	}

	public void sethGap(int hGap) {
		this.hGap = hGap;
	}

	public int getvGap() {
		return vGap;
	}

	public void setvGap(int vGap) {
		this.vGap = vGap;
	}

	public boolean isHorizontalCenter() {
		return isHorizontalCenter;
	}

	public void setHorizontalCenter(boolean isHorizontalCenter) {
		this.isHorizontalCenter = isHorizontalCenter;
	}

	public boolean isVerticalCenter() {
		return isVerticalCenter;
	}

	public void setVerticalCenter(boolean isVerticalCenter) {
		this.isVerticalCenter = isVerticalCenter;
	}

	public int gethParentGap() {
		return hParentGap;
	}

	public void sethParentGap(int hParentGap) {
		this.hParentGap = hParentGap;
	}

	public int getvParentGap() {
		return vParentGap;
	}

	public void setvParentGap(int vParentGap) {
		this.vParentGap = vParentGap;
	}
}
