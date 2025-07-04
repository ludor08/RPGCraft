package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class TraitTree
{
    List<Node> nodes;

    public TraitTree(List<Node> nodes)
    {
        this.nodes = nodes;
    }

    public Node GetNodeAtCoordinates(Vector2d coordinates)
    {
        for (Node node : nodes)
        {
            if (node.coordinates.equals(coordinates))
            {
                return node;
            }
        }

        return null;
    }

    public List<Node> GetSurroundingNodes(Node node)
    {
        Vector2d nodeCoordinates = node.coordinates;
        List<Node> surroundingNodes = new ArrayList<>();

        for (int x = -1; x < 2; x++)
        {
            for (int y = -1; y < 2; y++)
            {
                for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(x+"x " + y+"y");

                if (x != y && (x == 0 || y == 0))
                {
                    for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage("allowed through " + x+"x " + y+"y");

                    // if there is a node at nodeCoordinates + x y
                    if (GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x+x, nodeCoordinates.y+y)) != null)
                    {
                        // add it to the list of nodes
                        surroundingNodes.add(GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x+x, nodeCoordinates.y+y)));
                    }
                }
            }
        }

//        // if there is a node to the right
//        if (GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x+1, nodeCoordinates.y)) != null)
//        {
//            // add it to the list of nodes
//            surroundingNodes.add(GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x+1, nodeCoordinates.y)));
//        }
//
//        // if there is a node to the left
//        if (GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x-1, nodeCoordinates.y)) != null)
//        {
//            // add it to the list of nodes
//            surroundingNodes.add(GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x-1, nodeCoordinates.y)));
//        }
//
//        // if there is a node above this one
//        if (GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x, nodeCoordinates.y+1)) != null)
//        {
//            // add it to the list of nodes
//            surroundingNodes.add(GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x, nodeCoordinates.y+1)));
//        }
//
//        // if there is a node below this one
//        if (GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x, nodeCoordinates.y-1)) != null)
//        {
//            // add it to the list of nodes
//            surroundingNodes.add(GetNodeAtCoordinates(new Vector2d(nodeCoordinates.x, nodeCoordinates.y-1)));
//        }

        return surroundingNodes;
    }
}
