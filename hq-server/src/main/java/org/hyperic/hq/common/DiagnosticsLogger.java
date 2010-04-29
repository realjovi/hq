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

package org.hyperic.hq.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * The DiagnosticThread is a simple object running within the server that prints
 * diagnostic information to the server log for objects that have been
 * registered via addDiagnosticObject().
 * 
 * 
 */
@Component
public class DiagnosticsLogger implements Runnable {

    private Log log = LogFactory.getLog(DiagnosticsLogger.class);

    // The actual diagnostic thread
    private Thread diagnosticThread;

    // List of DiagnosticObjects, may want to convert to a Map if we ever
    // want to allow objects to be removed from the DiagnosticThread at
    // runtime.
    private List<DiagnosticObject> diagnosticObjects = new ArrayList<DiagnosticObject>();

    // How often the thread prints info
    private long interval = Long.getLong("org.hq.diagnostic.interval", 1000 * 60 * 10).longValue(); // 10
                                                                                                     // minutes

    public long getInterval() {
        return interval;
    }

    @PostConstruct
    public void startThread() {
        diagnosticThread = new Thread(this);
        diagnosticThread.setDaemon(true);
        diagnosticThread.start();
    }

    /**
     * Set the interval at which the DiagnosticThread will print status info
     * @param interval The interval in milliseconds. XXX -- Technically, access
     *        to interval should be synchronized
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void addDiagnosticObject(DiagnosticObject o) {
        synchronized (diagnosticObjects) {
            diagnosticObjects.add(o);
        }
    }

    public Collection<DiagnosticObject> getDiagnosticObjects() {
        synchronized (diagnosticObjects) {
            return new ArrayList<DiagnosticObject>(diagnosticObjects);
        }
    }

    public void run() {
        log.info("Starting Diagnostic Thread (interval=" + interval + " ms)");

        while (true) {
            try {
                Thread.sleep(interval);
                log.info("--- DIAGNOSTICS ---");
                synchronized (diagnosticObjects) {
                    for(DiagnosticObject o: diagnosticObjects) {
                        try {
                            log.info("[" + o + "] " + o.getStatus());
                        } catch (Throwable e) {
                            log.error("Error in diagnostics: " + e, e);
                        }
                    }
                }

            } catch (InterruptedException e) {
                log.warn("Diagnostic thread interrupted, shutting down.");
                break;
            } catch (Exception e) {
                log.warn("Error encountered while collecting diagnostics", e);
            }
        }
    }
}
