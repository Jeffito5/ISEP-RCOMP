RCOMP 2020-2021 Project - Sprint 3 planning
===========================================
### Sprint master: 1191596 ###

# 1. Sprint's backlog #

This sprint focus on changing static routers to based dynamic routing (OSPF). Using DHCPv4 service, VoIP service, HTTP service, configuring DNS servers to establish a DNS domains tree, enforcing NAT and establishing traffic access policies on routers.


# 2. Technical decisions and coordination #

#### Device naming: ####

To naming the devices we decide define the name this example following  format:

B1-HC-F1
*	B1-> Building + number of the building.
*	HC-> Name of what the device is representing.
*	F0-> Floor + number of the floor.

#### OSPF area ids used: ####

* Backbone OSPF area id - 0
* Building 1 OSPF area id - 1
* Building 2 OSPF area id - 2
* Building 3 OSPF area id - 3

#### VoIP phone numbers and prefix digits schema: ####

* Building 1 :
  + VOIP prefix F0 - 100
  + VOIP prefix F1 - 101

* Building 2 :
  + VOIP prefix F0 - 200
  + VOIP prefix F1 - 201

* Building 2 :  
 + VOIP prefix F0 - 300
 + VOIP prefix F1 - 301

#### DNS domain names to be used: ####

* Building 1 :
 + DNS domain name - rcomp-20-21-dh-g5
 + DNS server name - ns.rcomp-20-21-dh-g5

* Building 2 :
 + DNS domain name - building-2.rcomp-20-21-dh-g5
 + DNS server name - ns.building-2.rcomp-20-21-dh-g5

* Building 3 :
 + DNS domain name - building-3.rcomp-20-21-dh-g5
 + DNS server name - ns.building-3.rcomp-20-21-dh-g5

#### IPv4 node address of the DNS name server: ####

* Building 1 : 10.124.179.132

* Building 2 : 10.124.186.130

* Building 3 : 10.124.177.2



# 3. Subtasks assignment #

  * Task 3.1 - Danilton Lopes(1191240) -
    Update the campus.pkt layer three Packet Tracer simulation from the previous sprint, to include the described features in this sprint for building 1.

  * Task 3.2 - Luís Araújo (1190827) -
    Update the campus.pkt layer three Packet Tracer simulation from the previous sprint, to include the described features in this sprint for building 2.Final integration of each member’s Packet Tracer simulation into a single simulation.

  * Task 3.3 - Marisa Pereira (1191596) -
    Update the campus.pkt layer three Packet Tracer simulation from the previous sprint, to include the described features in this sprint for building 3.
