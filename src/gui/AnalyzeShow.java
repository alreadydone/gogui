//-----------------------------------------------------------------------------
// $Id$
// $Source$
//-----------------------------------------------------------------------------

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gtp.*;
import utils.*;

//-----------------------------------------------------------------------------

class AnalyzeShow
{
    public static void show(AnalyzeCommand command, gui.Board guiBoard,
                            go.Board board, String response) throws Gtp.Error
    {
        go.Point pointArg = command.getPointArg();
        Vector pointListArg = command.getPointListArg();
        guiBoard.clearAllSelect();
        for (int i = 0; i < pointListArg.size(); ++i)
            guiBoard.setSelect((go.Point)pointListArg.get(i), true);
        if (pointArg != null)
            guiBoard.setSelect(pointArg, true);
        int type = command.getType();
        String title = command.getTitle();
        int size = board.getSize();
        switch (type)
        {
        case AnalyzeCommand.BWBOARD:
            {
                String b[][] = Gtp.parseStringBoard(response, title, size);
                guiBoard.showBWBoard(b);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.CBOARD:
            {
                String b[][] = Gtp.parseStringBoard(response, title, size);
                guiBoard.showColorBoard(b);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.DBOARD:
            {
                double b[][] = Gtp.parseDoubleBoard(response, title, size);
                guiBoard.showDoubleBoard(b, command.getScale());
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.PLIST:
            {
                go.Point list[] = Gtp.parsePointList(response, size);
                guiBoard.showPointList(list);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.HPSTRING:
        case AnalyzeCommand.PSTRING:
            {
                go.Point list[] = Gtp.parsePointString(response, size);
                guiBoard.showPointList(list);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.PSPAIRS:
            {
                Vector pointList = new Vector(32, 32);
                Vector stringList = new Vector(32, 32);
                Gtp.parsePointStringList(response, pointList, stringList,
                                         size);
                guiBoard.showPointStringList(pointList, stringList);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.SBOARD:
            {
                String b[][] = Gtp.parseStringBoard(response, title, size);
                guiBoard.showStringBoard(b);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.VAR:
            {                    
                go.Point list[] = Gtp.parsePointString(response, size);
                guiBoard.showVariation(list, board.getToMove());
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.VARB:
            {
                go.Point list[] = Gtp.parsePointString(response, size);
                guiBoard.showVariation(list, go.Color.BLACK);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.VARW:
            {
                go.Point list[] = Gtp.parsePointString(response, size);
                guiBoard.showVariation(list, go.Color.WHITE);
                guiBoard.repaint();
            }
            break;
        case AnalyzeCommand.VARP:
            {
                go.Color c = getColor(board, pointArg, pointListArg);
                if (c != go.Color.EMPTY)
                {
                    go.Point list[] = Gtp.parsePointString(response, size);
                    guiBoard.showVariation(list, c);
                    guiBoard.repaint();
                }
            }
            break;
        case AnalyzeCommand.VARPO:
            {
                go.Color c = getColor(board, pointArg, pointListArg);
                if (c != go.Color.EMPTY)
                {
                    go.Point list[] = Gtp.parsePointString(response, size);
                    guiBoard.showVariation(list, c.otherColor());
                    guiBoard.repaint();
                }
            }
            break;
        }
    }

    private static go.Color getColor(go.Board board, go.Point pointArg,
                                     Vector pointListArg)
    {
        go.Color color = go.Color.EMPTY;
        if (pointArg != null)
            color = board.getColor(pointArg);
        if (color != go.Color.EMPTY)
            return color;
        for (int i = 0; i < pointListArg.size(); ++i)
        {
            go.Point point = (go.Point)pointListArg.get(i);
            color = board.getColor(point);
            if (color != go.Color.EMPTY)
                break;
        }
        return color;
    }
}

//-----------------------------------------------------------------------------
