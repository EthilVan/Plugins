package fr.ethilvan.bukkit.accounts;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.ethilvan.bukkit.api.accounts.Role;

public class RolesTest {

    private static AccountsConfig config;

    static {
        InputStream stream =
                AccountsPlugin.class.getResourceAsStream("/config-test.json");
        Gson gson = new GsonBuilder().create();
        config = gson.fromJson(new InputStreamReader(stream),
                AccountsConfig.class);
    }

    private Role admin;
    private Role modo;
    private Role vip;
    private Role basic;
    private Role guest;

    @Before
    public void setup() {
        Map<String, Role> roles = new RolesConfigReader(config.getRoles())
                .read();

        admin = roles.get("admin");
        modo  = roles.get("modo");
        vip   = roles.get("vip");
        basic = roles.get("basic");
        guest = roles.get("guest");
    }

    @Test
    public void testNames() {
        assertEquals("Admin", admin.getName());
        assertEquals("Modo",  modo.getName());
        assertEquals("Basic", basic.getName());
    }

    @Test
    public void testColors() {
        assertEquals(ChatColor.DARK_RED, admin.getColor());
        assertEquals(ChatColor.BLUE,     modo.getColor());
        assertEquals(ChatColor.WHITE,    basic.getColor());
    }

    @Test
    public void testStrictInheritance() {
        assertTrue(admin.inherit(admin));
        assertFalse(admin.strictInherit(admin));

        assertTrue(guest.inherit(guest));
        assertFalse(guest.strictInherit(guest));
    }

    @Test
    public void testInheritance() {
        assertTrue( admin.inherit(modo));
        assertTrue( admin.inherit(vip));
        assertTrue( admin.inherit(basic));
        assertTrue( admin.inherit(guest));

        assertFalse(modo.inherit(admin));
        assertTrue( modo.inherit(vip));
        assertTrue( modo.inherit(basic));
        assertTrue( modo.inherit(guest));

        assertFalse(vip.inherit(admin));
        assertFalse(vip.inherit(modo));
        assertTrue( vip.inherit(basic));
        assertTrue( vip.inherit(guest));

        assertFalse(basic.inherit(admin));
        assertFalse(basic.inherit(modo));
        assertFalse(basic.inherit(vip));
        assertTrue( basic.inherit(guest));

        assertFalse(guest.inherit(admin));
        assertFalse(guest.inherit(modo));
        assertFalse(guest.inherit(vip));
        assertFalse(guest.inherit(basic));
    }

    @Test
    public void testSubroles() {
        Set<Role> adminSubroles = new HashSet<Role>();
        adminSubroles.add(modo);
        adminSubroles.add(vip);
        adminSubroles.add(basic);
        adminSubroles.add(guest);
        assertEquals(adminSubroles, admin.getSubroles());

        Set<Role> modoSubroles = new HashSet<Role>();
        modoSubroles.add(vip);
        modoSubroles.add(basic);
        modoSubroles.add(guest);
        assertEquals(modoSubroles, modo.getSubroles());

        Set<Role> vipSubroles = new HashSet<Role>();
        vipSubroles.add(basic);
        vipSubroles.add(guest);
        assertEquals(vipSubroles, vip.getSubroles());

        Set<Role> basicSubroles = new HashSet<Role>();
        basicSubroles.add(guest);
        assertEquals(basicSubroles, basic.getSubroles());

        Set<Role> guestSubroles = new HashSet<Role>();
        assertEquals(guestSubroles, guest.getSubroles());
    }
}
