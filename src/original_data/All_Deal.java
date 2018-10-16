package original_data;

import java.sql.SQLException;

public class All_Deal {
	/**
	 * 执行到哪个报错就去单独执行那个处理数据的类
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException{
		Y_basic_socialgroups_1.dealData();
		y_basic_position_2.dealData();
		Y_basic_member_3.dealData();
		y_basicentries_memberdistribution_4.dealData();
		y_basic_imaccount_5.dealData();
		y_basic_socialgroupsguestbook_6.dealData();
		y_basic_socialgroupscontact_7.dealData();
		y_basic_group_8.dealData();
		y_basic_grouppeople_9.dealData();
		y_basic_socialgroupsinform_10.dealData();
		y_basic_socialgroupsinformrecord_11.dealData();
		y_basic_socialgroupsdynamic_12.dealData();
		y_wallactivity_13.dealData();
		y_wallreply_14.dealData();
		y_basic_socialgroupssupply_15.dealData();
		y_basic_socialgroupsdemand_16.dealData();
		y_basic_membercompany_17.dealData();
		t_news_headline_18.dealData();
		y_comment_19.dealData();
		y_basic_socialgroupsabout_20.dealData();
	}
}
