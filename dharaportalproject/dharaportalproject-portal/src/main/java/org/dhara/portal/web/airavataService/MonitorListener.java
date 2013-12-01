/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package org.dhara.portal.web.airavataService;

import org.apache.airavata.ws.monitor.EventData;
import org.apache.airavata.ws.monitor.EventDataListener;
import org.apache.airavata.ws.monitor.EventDataRepository;
import org.apache.airavata.ws.monitor.Monitor;

import java.util.Observable;

public class MonitorListener extends Observable implements EventDataListener {

    /**
     * Send notification message to observers
     * @param eventDataRepo
     * @param eventData
     */
    public void notify(EventDataRepository eventDataRepo, EventData eventData) {
        MonitorMessage monitorMessage = new MonitorMessage();
        monitorMessage.setMesssage(eventData.getMessage());
        monitorMessage.setStatusText(eventData.getStatusText());
        monitorMessage.setTimestamp(eventData.getTimestamp());
        monitorMessage.setComonent(eventData.getIDText());
        setChanged();
        notifyObservers(monitorMessage);
    }


    public void setExperimentMonitor(Monitor monitor) {
        // TODO Auto-generated method stub

    }


    public void monitoringPreStart() {
        // TODO Auto-generated method stub

    }


    public void monitoringPostStart() {
        // TODO Auto-generated method stub

    }


    public void monitoringPreStop() {
        // TODO Auto-generated method stub

    }


    public void monitoringPostStop() {
        // TODO Auto-generated method stub

    }


    public void onFail(EventData failNotification) {
        // TODO Auto-generated method stub

    }


    public void onCompletion(EventData completionNotification) {
        // TODO Auto-generated method stub

    }

}
