package cn.xkmc6.xkitemmanage.libs.extensions.org.bukkit.entity.Player;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.entity.Player;

@Extension
public class PlayerExtension {
  public static void helloWorld(@This Player thiz) {
    System.out.println("hello world!");
  }
}