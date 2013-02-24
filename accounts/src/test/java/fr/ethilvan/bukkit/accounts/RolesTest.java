package fr.ethilvan.bukkit.accounts;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

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
    public void testInheritance() {
        assertTrue( admin.inherit(admin));
        assertTrue( admin.inherit(modo));
        assertTrue( admin.inherit(vip));
        assertTrue( admin.inherit(basic));
        assertTrue( admin.inherit(guest));

        assertFalse(admin.strictInherit(admin));
        assertTrue( admin.strictInherit(modo));
        assertTrue( admin.strictInherit(vip));
        assertTrue( admin.strictInherit(basic));
        assertTrue( admin.strictInherit(guest));

        assertFalse(modo.inherit(admin));
        assertTrue( modo.inherit(modo));
        assertTrue( modo.inherit(vip));
        assertTrue( modo.inherit(basic));
        assertTrue( modo.inherit(guest));

        assertFalse(modo.strictInherit(admin));
        assertFalse(modo.strictInherit(modo));
        assertTrue( modo.strictInherit(vip));
        assertTrue( modo.strictInherit(basic));
        assertTrue( modo.strictInherit(guest));

        assertFalse(vip.inherit(admin));
        assertFalse(vip.inherit(modo));
        assertTrue( vip.inherit(vip));
        assertTrue( vip.inherit(basic));
        assertTrue( vip.inherit(guest));

        assertFalse(vip.strictInherit(admin));
        assertFalse(vip.strictInherit(modo));
        assertFalse(vip.strictInherit(vip));
        assertTrue( vip.strictInherit(basic));
        assertTrue( vip.strictInherit(guest));

        assertFalse(basic.inherit(admin));
        assertFalse(basic.inherit(modo));
        assertFalse(basic.inherit(vip));
        assertTrue( basic.inherit(basic));
        assertTrue( basic.inherit(guest));

        assertFalse(basic.strictInherit(admin));
        assertFalse(basic.strictInherit(modo));
        assertFalse(basic.strictInherit(vip));
        assertFalse(basic.strictInherit(basic));
        assertTrue( basic.strictInherit(guest));

        assertFalse(guest.inherit(admin));
        assertFalse(guest.inherit(modo));
        assertFalse(guest.inherit(vip));
        assertFalse(guest.inherit(basic));
        assertTrue( guest.inherit(guest));

        assertFalse(guest.strictInherit(admin));
        assertFalse(guest.strictInherit(modo));
        assertFalse(guest.strictInherit(vip));
        assertFalse(guest.strictInherit(basic));
        assertFalse(guest.strictInherit(guest));
    }
}
