package com.kannur.kalolsavam;

public class Points {
	String sl_no;
	String district;
	String point;
	String total;
	String hs;
	String hss;
	String ara;
	String san;
	
	public Points(String sl_no,String district,String point,String hs,String hss,String ara,String sans)
	{
		this.sl_no = sl_no;
		this.district = district;
		this.total = point;
		this.hs=hs;
		this.hss=hss;
		this.ara=ara;
		this.san=sans;
	}
	public Points(String sl_no,String district,String point)
	{
		this.sl_no = sl_no;
		this.district = district;
		this.point = point;
		
	}
}
