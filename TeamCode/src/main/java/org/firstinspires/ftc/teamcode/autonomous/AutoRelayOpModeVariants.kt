package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Auto Relay Blue Op Mode")
class AutoRelayBlueOpMode: AutoRelayOpMode(1, 2.8)

@Autonomous(name = "Auto Relay Red Op Mode")
class AutoRelayRedOpMode: AutoRelayOpMode(-1, 2.8)
