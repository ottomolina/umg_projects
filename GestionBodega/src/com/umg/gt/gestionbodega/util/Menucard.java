package com.umg.gt.gestionbodega.util;

import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zul.Panel;

@SuppressWarnings("serial")
public class Menucard extends HtmlMacroComponent{
	
	Panel Card;
	
	@Override
	public void afterCompose() {
		super.afterCompose();
		Card = (Panel) this.getChildren().get(0);
		
	}
	
	
}
