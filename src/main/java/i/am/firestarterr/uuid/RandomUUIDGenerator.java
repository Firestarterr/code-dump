package i.am.firestarterr.uuid;

import java.util.UUID;

public class RandomUUIDGenerator {
    public static void main(String[] args) {
        System.out.println("CREATE TEMP TABLE uuid_list\n" +
                "(\n" +
                "    siralama int,\n" +
                "    guid     uuid\n" +
                ");");
        for (int i = 0; i < 300; i++) {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            System.out.println(randomUUIDString);
//            System.out.println("insert into uuid_list (siralama, guid) values\n(" + i + " ,'" + randomUUIDString + "');");
        }
        System.out.println("CREATE OR REPLACE FUNCTION LoopThrough()\n" +
                "    RETURNS VOID\n" +
                "AS\n" +
                "$$\n" +
                "DECLARE\n" +
                "    t_curs cursor for\n" +
                "        select *\n" +
                "        from service_engineer_dispatcher;\n" +
                "    t_row    service_engineer_dispatcher%rowtype;\n" +
                "    iterator float4 := 0;\n" +
                "BEGIN\n" +
                "    FOR t_row in t_curs\n" +
                "        LOOP\n" +
                "            update service_engineer_dispatcher\n" +
                "            set id = (select guid from uuid_list where siralama = iterator)\n" +
                "            where current of t_curs;\n" +
                "            iterator := iterator + 1;\n" +
                "        END LOOP;\n" +
                "END;\n" +
                "$$\n" +
                "    LANGUAGE plpgsql;\n" +
                "\n" +
                "select LoopThrough();\n" +
                "\n" +
                "drop table uuid_list;\n" +
                "drop function LoopThrough();");
    }
}