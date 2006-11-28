/*
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 * 
 * Copyright (C) [2004, 2005, 2006], Hyperic, Inc.
 * This file is part of HQ.
 * 
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

package org.hyperic.hq.ui.action.portlet.availsummary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.hq.bizapp.shared.AppdefBoss;
import org.hyperic.hq.bizapp.shared.MeasurementBoss;
import org.hyperic.hq.ui.util.ContextUtils;
import org.hyperic.hq.ui.util.DashboardUtils;
import org.hyperic.hq.ui.WebUser;
import org.hyperic.hq.ui.Constants;
import org.hyperic.hq.appdef.shared.AppdefEntityID;
import org.hyperic.hq.appdef.shared.AppdefResourceValue;
import org.hyperic.hq.measurement.MeasurementConstants;
import org.hyperic.util.pager.PageList;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;

/**
 * This action class is used by the Availability Summary portlet.  It's main
 * use is to generate the JSON objects required for display into the UI.
 */
public class ViewAction extends TilesAction {

    Log _log = LogFactory.getLog("AVAIL SUMMARY");

    public ActionForward execute(ComponentContext context,
                                 ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        ServletContext ctx = getServlet().getServletContext();
        AppdefBoss appdefBoss = ContextUtils.getAppdefBoss(ctx);
        MeasurementBoss mBoss = ContextUtils.getMeasurementBoss(ctx);

        WebUser user = (WebUser) request.getSession().getAttribute(
            Constants.WEBUSER_SES_ATTR);

        String key = ".dashContent.availsummary.resources";

        List entityIds = DashboardUtils.preferencesAsEntityIds(key, user);
        AppdefEntityID[] arrayIds =
            (AppdefEntityID[])entityIds.toArray(new AppdefEntityID[0]);

        int count = Integer.parseInt(user.
            getPreference(PropertiesForm.NUM_TO_SHOW));

        int sessionId = user.getSessionId().intValue();
        PageList resources = appdefBoss.findByIds(sessionId, arrayIds);

        Map res = new HashMap();
        for (Iterator i = resources.iterator(); i.hasNext(); ) {
            AppdefResourceValue rValue = (AppdefResourceValue)i.next();
            String type = rValue.getAppdefResourceTypeValue().getName();

            AvailSummary summary = (AvailSummary)res.get(type);
            if (summary == null) {
                summary = new AvailSummary(type);
                res.put(type, summary);
            }
                
            double avail = mBoss.getAvailability(sessionId,
                                                 rValue.getEntityId());
            summary.setAvailability(avail);
        }

        JSONObject availSummary = new JSONObject();
        List types = new ArrayList();

        TreeSet sortedSet = new TreeSet(new AvailSummaryComparator());
        sortedSet.addAll(res.values());

        for (Iterator i = sortedSet.iterator(); i.hasNext() && count-- > 0; ) {
            AvailSummary summary = (AvailSummary)i.next();
            JSONObject typeSummary = new JSONObject();
            typeSummary.put("resourceTypeName", summary.getTypeName());
            typeSummary.put("numUp", summary.getNumUp());
            typeSummary.put("numDown", summary.getNumDown());

            types.add(typeSummary);
        }

        availSummary.put("availSummary", types);

        _log.info(availSummary.toString(2));
        
        return null;
    }

    private class AvailSummary {
        private String _typeName;
        private int _numUp = 0;
        private int _numDown = 0;

        public AvailSummary(String typeName) {
            _typeName = typeName;
        }

        public void setAvailability(double avail) {
            if (avail == MeasurementConstants.AVAIL_UP) {
                _numUp++;
            } else {
                _numDown++;
            }
        }

        public String getTypeName() {
            return _typeName;
        }

        public int getNumUp() {
            return _numUp;
        }

        public int getNumDown() {
            return _numDown;
        }

        public double getAvailPercentage() {
            return (double)_numUp/(_numDown + _numUp);
        }
    }

    private class AvailSummaryComparator implements Comparator {

        public int compare(Object o1, Object o2) {
            AvailSummary s1 = (AvailSummary)o1;
            AvailSummary s2 = (AvailSummary)o2;

            if (s1.getAvailPercentage() == s2.getAvailPercentage()) {
                // Sort on type name if equal avail percentage
                return s1.getTypeName().compareTo(s2.getTypeName());
            } else if (s1.getAvailPercentage() < s2.getAvailPercentage()) {
                return -1;
            } else {
                return 1;
            }
        }

    }
}
