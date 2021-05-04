package i.am.firestarterr.scheduleranalysis;

public class TeamCreate {

//    private void dolittle() {
//        Section sectionFIELD_SERVICE = new Section();
//        sectionFIELD_SERVICE.setDeleted(false);
//        sectionFIELD_SERVICE.setType(SectionType.FIELD_SERVICE);
//        sectionFIELD_SERVICE = entityDao.merge(sectionFIELD_SERVICE);
//        Section sectionWORKSHOP = new Section();
//        sectionWORKSHOP.setDeleted(false);
//        sectionWORKSHOP.setType(SectionType.WORKSHOP);
//        sectionWORKSHOP = entityDao.merge(sectionWORKSHOP);
//        Section sectionMAINTENANCE = new Section();
//        sectionMAINTENANCE.setDeleted(false);
//        sectionMAINTENANCE.setType(SectionType.MAINTENANCE);
//        sectionMAINTENANCE = entityDao.merge(sectionMAINTENANCE);
//        Section sectionEQUIPMENT_MANAGEMENT = new Section();
//        sectionEQUIPMENT_MANAGEMENT.setDeleted(false);
//        sectionEQUIPMENT_MANAGEMENT.setType(SectionType.EQUIPMENT_MANAGEMENT);
//        sectionEQUIPMENT_MANAGEMENT = entityDao.merge(sectionEQUIPMENT_MANAGEMENT);
//        Section sectionERS = new Section();
//        sectionERS.setDeleted(false);
//        sectionERS.setType(SectionType.ERS);
//        sectionERS = entityDao.merge(sectionERS);
//        Section sectionCRS_ENG = new Section();
//        sectionCRS_ENG.setDeleted(false);
//        sectionCRS_ENG.setType(SectionType.CRS_ENG);
//        sectionCRS_ENG = entityDao.merge(sectionCRS_ENG);
//        Section sectionCRS_PT = new Section();
//        sectionCRS_PT.setDeleted(false);
//        sectionCRS_PT.setType(SectionType.CRS_PT);
//        sectionCRS_PT = entityDao.merge(sectionCRS_PT);
//        Section sectionCRS_OTHER = new Section();
//        sectionCRS_OTHER.setDeleted(false);
//        sectionCRS_OTHER.setType(SectionType.CRS_OTHER);
//        sectionCRS_OTHER = entityDao.merge(sectionCRS_OTHER);
//        Section sectionCRS_HS = new Section();
//        sectionCRS_HS.setDeleted(false);
//        sectionCRS_HS.setType(SectionType.CRS_HS);
//        sectionCRS_HS = entityDao.merge(sectionCRS_HS);
//        Section sectionMS_WS = new Section();
//        sectionMS_WS.setDeleted(false);
//        sectionMS_WS.setType(SectionType.MS_WS);
//        sectionMS_WS = entityDao.merge(sectionMS_WS);
//        Section sectionMS_MS = new Section();
//        sectionMS_MS.setDeleted(false);
//        sectionMS_MS.setType(SectionType.MS_MS);
//        sectionMS_MS = entityDao.merge(sectionMS_MS);
//        Section sectionIL = new Section();
//        sectionIL.setDeleted(false);
//        sectionIL.setType(SectionType.IL);
//        sectionIL = entityDao.merge(sectionIL);
//
//
//        Organization crcOrg = new Organization();
//        crcOrg.setOrgunit("51010096");
//        Team teamERS = new Team();
//        teamERS.setDeleted(false);
//        teamERS.setName("ERS Takım 1");
//        teamERS.setSection(sectionERS);
//        teamERS.setOrganization(crcOrg);
//        teamERS = entityDao.merge(teamERS);
//        Team teamCRS_ENG = new Team();
//        teamCRS_ENG.setDeleted(false);
//        teamCRS_ENG.setName("CRS Motor Takım 1");
//        teamCRS_ENG.setSection(sectionCRS_ENG);
//        teamCRS_ENG.setOrganization(crcOrg);
//        teamCRS_ENG = entityDao.merge(teamCRS_ENG);
//        Team teamCRS_PT = new Team();
//        teamCRS_PT.setDeleted(false);
//        teamCRS_PT.setName("CRS Power Train Takım 1");
//        teamCRS_PT.setSection(sectionCRS_PT);
//        teamCRS_PT.setOrganization(crcOrg);
//        teamCRS_PT = entityDao.merge(teamCRS_PT);
//        Team teamCRS_OTHER = new Team();
//        teamCRS_OTHER.setDeleted(false);
//        teamCRS_OTHER.setName("CRS Diğer Takım 1");
//        teamCRS_OTHER.setSection(sectionCRS_OTHER);
//        teamCRS_OTHER.setOrganization(crcOrg);
//        teamCRS_OTHER = entityDao.merge(teamCRS_OTHER);
//        Team teamCRS_HS = new Team();
//        teamCRS_HS.setDeleted(false);
//        teamCRS_HS.setName("CRS Hidrolik Takım 1");
//        teamCRS_HS.setSection(sectionCRS_HS);
//        teamCRS_HS.setOrganization(crcOrg);
//        teamCRS_HS = entityDao.merge(teamCRS_HS);
//        Team teamMS_WS = new Team();
//        teamMS_WS.setDeleted(false);
//        teamMS_WS.setName("MS Workshop Takım 1");
//        teamMS_WS.setSection(sectionMS_WS);
//        teamMS_WS.setOrganization(crcOrg);
//        teamMS_WS = entityDao.merge(teamMS_WS);
//        Team teamMS_MS = new Team();
//        teamMS_MS.setDeleted(false);
//        teamMS_MS.setName("MS Machine Shop Takım 1");
//        teamMS_MS.setSection(sectionMS_MS);
//        teamMS_MS.setOrganization(crcOrg);
//        teamMS_MS = entityDao.merge(teamMS_MS);
//        Team teamIL = new Team();
//        teamIL.setDeleted(false);
//        teamIL.setName("IL Internal Logistics Takım 1");
//        teamIL.setSection(sectionIL);
//        teamIL.setOrganization(crcOrg);
//        teamIL = entityDao.merge(teamIL);
//        Team[] teamArray = new Team[7];
//        teamArray[0] = teamERS;
//        teamArray[1] = teamCRS_ENG;
//        teamArray[2] = teamCRS_PT;
//        teamArray[3] = teamCRS_OTHER;
//        teamArray[4] = teamCRS_HS;
//        teamArray[5] = teamMS_WS;
//        teamArray[6] = teamMS_MS;
//
//        for (Technician technician : list) {
//            if (technician.getOrganization().getOrgunit().equals("51010096")) {
//                technician.setTeam(teamArray[Integer.parseInt(technician.getBpNo().substring(technician.getBpNo().length() - 1)) % 7]);
//            } else {
//                int randNumber = Integer.parseInt(technician.getBpNo().substring(technician.getBpNo().length() - 1)) % 4;
//                List<Team> teams = entityDao.getTeamsOfOrganization(technician.getOrganization().getOrgunit());
//                if (randNumber == 0) {
//                    Team techTeam = new Team();
//                    boolean teaminserted = false;
//                    for (Team team : teams) {
//                        if (team.getName().equals("Dış Servis Takım 1")) {
//                            techTeam = team;
//                            teaminserted = true;
//                            break;
//                        }
//                    }
//                    if (!teaminserted) {
//                        techTeam.setDeleted(false);
//                        techTeam.setName("Dış Servis Takım 1");
//                        techTeam.setSection(sectionFIELD_SERVICE);
//                        techTeam.setOrganization(technician.getOrganization());
//                        techTeam = entityDao.merge(techTeam);
//                    }
//                    technician.setTeam(techTeam);
//                }
//                if (randNumber == 1) {
//                    Team techTeam = new Team();
//                    boolean teaminserted = false;
//                    for (Team team : teams) {
//                        if (team.getName().equals("Atölye Takım 1")) {
//                            techTeam = team;
//                            teaminserted = true;
//                            break;
//                        }
//                    }
//                    if (!teaminserted) {
//                        techTeam.setDeleted(false);
//                        techTeam.setName("Atölye Takım 1");
//                        techTeam.setSection(sectionWORKSHOP);
//                        techTeam.setOrganization(technician.getOrganization());
//                        techTeam = entityDao.merge(techTeam);
//                    }
//                    technician.setTeam(techTeam);
//                }
//                if (randNumber == 2) {
//                    Team techTeam = new Team();
//                    boolean teaminserted = false;
//                    for (Team team : teams) {
//                        if (team.getName().equals("Bakım Takım 1")) {
//                            techTeam = team;
//                            teaminserted = true;
//                            break;
//                        }
//                    }
//                    if (!teaminserted) {
//                        techTeam.setDeleted(false);
//                        techTeam.setName("Bakım Takım 1");
//                        techTeam.setSection(sectionMAINTENANCE);
//                        techTeam.setOrganization(technician.getOrganization());
//                        techTeam = entityDao.merge(techTeam);
//                    }
//                    technician.setTeam(techTeam);
//                }
//                if (randNumber == 3) {
//                    Team techTeam = new Team();
//                    boolean teaminserted = false;
//                    for (Team team : teams) {
//                        if (team.getName().equals("Ekipman Yönetimi Takım 1")) {
//                            techTeam = team;
//                            teaminserted = true;
//                            break;
//                        }
//                    }
//                    if (!teaminserted) {
//                        techTeam.setDeleted(false);
//                        techTeam.setName("Ekipman Yönetimi Takım 1");
//                        techTeam.setSection(sectionEQUIPMENT_MANAGEMENT);
//                        techTeam.setOrganization(technician.getOrganization());
//                        techTeam = entityDao.merge(techTeam);
//                    }
//                    technician.setTeam(techTeam);
//                }
//            }
//            entityDao.merge(technician);
//        }
//    }

}
