/**
 *
 * @copyright &copy; 2010 - 2020, Fraunhofer-Gesellschaft zur Foerderung der
 *  angewandten Forschung e.V. All rights reserved.
 *
 * BSD 3-Clause License
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1.  Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 * 2.  Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 * 3.  Neither the name of the copyright holder nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * We kindly request you to use one or more of the following phrases to refer
 * to foxBMS in your hardware, software, documentation or advertising
 * materials:
 *
 * &Prime;This product uses parts of foxBMS&reg;&Prime;
 *
 * &Prime;This product includes parts of foxBMS&reg;&Prime;
 *
 * &Prime;This product is derived from foxBMS&reg;&Prime;
 *
 */

/**
 * @file    contactor_cfg.c
 * @author  foxBMS Team
 * @date    23.09.2015 (date of creation)
 * @ingroup DRIVERS_CONF
 * @prefix  CONT
 *
 * @brief   Configuration for the driver for the contactors
 *
 */

/*================== Includes =============================================*/
#include "contactor_cfg.h"

// #include "database.h"
// #include <float.h>
// #include <math.h>

/*================== Macros and Definitions ===============================*/

/*================== Constant and Variable Definitions ====================*/

// We define the mapping from the Relay channel to Relay control IO pins in three situations:

// 1. Use true relays. 2. Use emulated Relays using LEDs
#define CONT_USE_RELAYS 1

#if CONT_USE_RELAYS == 1 // true relays
const CONT_CONFIG_s cont_contactors_config[BS_NR_OF_CONTACTORS] = {
    {CONT_MAIN_PLUS_CONTROL,        CONT_IO_MAIN_PLUS_FEEDBACK,         CONT_FEEDBACK_NORMALLY_OPEN},
    {CONT_PRECHARGE_PLUS_CONTROL,   CONT_PRECHARGE_PLUS_FEEDBACK,       CONT_FEEDBACK_NORMALLY_OPEN},
    {CONT_MAIN_MINUS_CONTROL,       CONT_IO_MAIN_MINUS_FEEDBACK,        CONT_FEEDBACK_NORMALLY_OPEN},
    {CONT_CHARGE_MAIN_PLUS_CONTROL,         CONT_CHARGE_MAIN_PLUS_FEEDBACK,         CONT_FEEDBACK_NORMALLY_OPEN},
    {CONT_CHARGE_PRECHARGE_PLUS_CONTROL,    CONT_CHARGE_PRECHARGE_PLUS_FEEDBACK,    CONT_FEEDBACK_NORMALLY_OPEN},
    {CONT_CHARGE_MAIN_MINUS_CONTROL,        CONT_CHARGE_MAIN_MINUS_FEEDBACK,        CONT_FEEDBACK_NORMALLY_OPEN}
};
#elif CONT_USE_RELAYS == 2 // Relays emulated by LEDs 
const CONT_CONFIG_s cont_contactors_config[BS_NR_OF_CONTACTORS] = {
    {CONT_MAIN_PLUS_CONTROL,        CONT_IO_MAIN_PLUS_FEEDBACK,         CONT_HAS_NO_FEEDBACK},
    {CONT_PRECHARGE_PLUS_CONTROL,   CONT_PRECHARGE_PLUS_FEEDBACK,       CONT_HAS_NO_FEEDBACK},
    {CONT_MAIN_MINUS_CONTROL,       CONT_IO_MAIN_MINUS_FEEDBACK,        CONT_HAS_NO_FEEDBACK},
    {CONT_CHARGE_MAIN_PLUS_CONTROL,         CONT_CHARGE_MAIN_PLUS_FEEDBACK,         CONT_HAS_NO_FEEDBACK},
    {CONT_CHARGE_PRECHARGE_PLUS_CONTROL,    CONT_CHARGE_PRECHARGE_PLUS_FEEDBACK,    CONT_HAS_NO_FEEDBACK},
    {CONT_CHARGE_MAIN_MINUS_CONTROL,        CONT_CHARGE_MAIN_MINUS_FEEDBACK,        CONT_HAS_NO_FEEDBACK}
};
#endif // CONT_USE_RELAYS  

CONT_ELECTRICAL_STATE_s cont_contactor_states[BS_NR_OF_CONTACTORS] = {
    {0,    CONT_SWITCH_OFF},
    {0,    CONT_SWITCH_OFF},
    {0,    CONT_SWITCH_OFF},
    {0,    CONT_SWITCH_OFF},
    {0,    CONT_SWITCH_OFF},
    {0,    CONT_SWITCH_OFF},
};

const uint8_t cont_contactors_config_length = sizeof(cont_contactors_config)/sizeof(cont_contactors_config[0]);
const uint8_t cont_contactors_states_length = sizeof(cont_contactor_states)/sizeof(cont_contactor_states[0]);
