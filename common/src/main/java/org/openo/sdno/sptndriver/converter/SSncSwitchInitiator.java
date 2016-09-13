/*
 * Copyright 2016 ZTE, Inc. and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.sptndriver.converter;

import org.openo.sdno.sptndriver.models.north.NMplsTePolicy;
import org.openo.sdno.sptndriver.models.north.NPathProtectPolicy;
import org.openo.sdno.sptndriver.models.south.SSncSwitch;

/**
 * The class to initiate snc switch.
 */
public class SSncSwitchInitiator {

  private static final int DEFAULT_WTR = 300;
  private static final int DEFAULT_HOLDOFF_TIME = 0;
  //private static final int DEFAULT_REROUTE_WTR = 50;
  private static final SSncSwitch.SwitchModeEnum DEFAULT_SWITCH_MODE
      = SSncSwitch.SwitchModeEnum.DOUBLE_END_SWITCH;
  /**
   * NBI wtr in us, SBI wtr in seconds.
   */
  private static final int WTR_MULTIPLIER = 1000000;

  /**
   * Init PW snc switch.
   *
   * @param sncId        Service(Eline) UUID, not PW UUID.
   * @param hasSncSwitch Whether the service has PW snc switch.
   * @return PW snc switch.
   */
  public static SSncSwitch initPwSncSwitch(String sncId, boolean hasSncSwitch) {
    if (!hasSncSwitch) {
      return null;
    }
    SSncSwitch pwSncSwitch = new SSncSwitch();

    pwSncSwitch.setSncId(sncId);
    pwSncSwitch.setLayerRate(SSncSwitch.LayerRateEnum.PW);

    pwSncSwitch.setLinearProtectionType(
        SSncSwitch.LinearProtectionTypeEnum.PATH_PROTECTION_1_TO_1);


    pwSncSwitch.setLinearProtectionProtocol(SSncSwitch.LinearProtectionProtocolEnum.APS);
    pwSncSwitch.setSwitchMode(DEFAULT_SWITCH_MODE);
    pwSncSwitch.setRevertiveMode(SSncSwitch.RevertiveModeEnum.REVERTIVE);
    pwSncSwitch.setWtr(Integer.toString(DEFAULT_WTR));
    pwSncSwitch.setHoldOffTime(Integer.toString(DEFAULT_HOLDOFF_TIME));
    //pwSncSwitch.setRerouteRevertiveMode(
    // Integer.getInteger(SRevertiveMode.NO_REVERTIVE.toString()));
    //pwSncSwitch.setRerouteWtr(DEFAULT_REROUTE_WTR);

    return pwSncSwitch;
  }

  /**
   * Init LSP snc switch.
   *
   * @param policy NBI MPLS TE policy.
   * @return LSP snc switch.
   */
  public static SSncSwitch initLspSncSwitch(NMplsTePolicy policy) {
    SSncSwitch sncSwitch = new SSncSwitch();
    boolean hasProt = false;
    boolean coRoute = false;
    SSncSwitch.RevertiveModeEnum revertiveMode = SSncSwitch.RevertiveModeEnum.REVERTIVE;
    int wtr = DEFAULT_WTR;
    if (policy != null && policy.getCoRoute() != null) {
      coRoute = policy.getCoRoute();
    }

    if (policy != null && policy.getPathProtectPolicy() != null) {
      NPathProtectPolicy pathProtectPolicy = policy.getPathProtectPolicy();
      hasProt = true;
      if (pathProtectPolicy.getRevertive() != null
          && !pathProtectPolicy.getRevertive()) {
        revertiveMode = SSncSwitch.RevertiveModeEnum.NO_REVERTIVE;
      }
      if (pathProtectPolicy.getWtr() != null) {
        wtr = pathProtectPolicy.getWtr() / WTR_MULTIPLIER;
      }
    }

    // snc id, configured by controller since drive doesn't know UUID yet
    sncSwitch.setSncId(null);
    sncSwitch.setLayerRate(SSncSwitch.LayerRateEnum.LSP);
    sncSwitch.setLinearProtectionType(getLspProtType(hasProt, coRoute));
    sncSwitch.setLinearProtectionProtocol(SSncSwitch.LinearProtectionProtocolEnum.APS);
    sncSwitch.setSwitchMode(DEFAULT_SWITCH_MODE);
    sncSwitch.setRevertiveMode(revertiveMode);
    sncSwitch.setWtr(Integer.toString(wtr));
    sncSwitch.setHoldOffTime(Integer.toString(DEFAULT_HOLDOFF_TIME));
    //sncSwitch.setRerouteRevertiveMode(Integer.getInteger(SRevertiveMode.NO_REVERTIVE.toString()));
    //sncSwitch.setRerouteWtr(DEFAULT_REROUTE_WTR);

    return sncSwitch;
  }

  /**
   * Init protection type.
   *
   * @param hasProt     Whether LSP has protection.
   * @param needReroute Whether LSP need reroute when it is down.
   * @return LSP protection type.
   */
  private static SSncSwitch.LinearProtectionTypeEnum getLspProtType(boolean hasProt,
                                                                    boolean needReroute) {
    if (!hasProt) {
      if (!needReroute) {
        return SSncSwitch.LinearProtectionTypeEnum.UNPROTECTED;
      } else {
        return SSncSwitch.LinearProtectionTypeEnum.UNPROTECTED_WITH_RECOVERY;
      }
    } else {
      if (!needReroute) {
        return SSncSwitch.LinearProtectionTypeEnum.PATH_PROTECTION_1_TO_1;
      } else {
        return SSncSwitch.LinearProtectionTypeEnum.WITH_RECOVERY_1_TO_1;
      }
    }
  }

}
