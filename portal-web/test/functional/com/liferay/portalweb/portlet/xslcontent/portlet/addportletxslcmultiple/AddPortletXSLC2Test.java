/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.xslcontent.portlet.addportletxslcmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletXSLC2Test extends BaseTestCase {
	public void testAddPortletXSLC2() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=XSL Content Test Page",
					RuntimeVariables.replace("XSL Content Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//li[@id='_145_addContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_addContent']/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'add-application')]/a");
				assertEquals(RuntimeVariables.replace(
						"Content and Applications"),
					selenium.getText(
						"//li[contains(@class,'add-application')]/a"));
				selenium.clickAt("//li[contains(@class,'add-application')]/a",
					RuntimeVariables.replace("Content and Applications"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-live-search/aui-live-search-min.js')]");

				boolean searchApplicationsNotVisible = selenium.isVisible(
						"//input[@id='layout_configuration_content']");

				if (searchApplicationsNotVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Applications"),
					selenium.getText(
						"//div[@id='portal_add_panel']/ul/li/span/a[contains(.,'Applications')]"));
				selenium.clickAt("//div[@id='portal_add_panel']/ul/li/span/a[contains(.,'Applications')]",
					RuntimeVariables.replace("Applications"));

			case 2:
				selenium.waitForVisible(
					"//input[@id='layout_configuration_content']");
				selenium.sendKeys("//input[@id='layout_configuration_content']",
					RuntimeVariables.replace("x"));
				selenium.waitForVisible("//li[@title='XSL Content']/p/a");
				selenium.clickAt("//li[@title='XSL Content']/p/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible("//div[1]/section");
				assertTrue(selenium.isVisible("//div[1]/section"));
				selenium.waitForVisible("//div[2]/section");
				assertTrue(selenium.isVisible("//div[2]/section"));

			case 100:
				label = -1;
			}
		}
	}
}