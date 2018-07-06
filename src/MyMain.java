/* Soot - a J*va Optimization Framework
 * Copyright (C) 2008 Eric Bodden
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */
import java.util.Map;
import java.util.Iterator;

import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.AbstractDefinitionStmt;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JArrayRef;
import soot.jimple.StaticFieldRef;
import soot.Value;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;


import soot.Body;
import soot.BodyTransformer;
import soot.G;
import soot.PackManager;
import soot.Transform;
import soot.toolkits.graph.ExceptionalUnitGraph;

public class MyMain {
    public static void main(String[] args) {

        PackManager
                .v()
                .getPack("jtp")
                .add(new Transform("jtp.GotoInstrumenter", GotoInstrumenter.v()));

        System.out.println(Scene.v().defaultClassPath());
        SootClass sootClass = Scene.v().loadClassAndSupport("test");


        if (sootClass == null || !(sootClass instanceof SootClass)) {
            System.out.println("sootClass not initialized");
            System.exit(0);
        } else {
            System.out.println(sootClass.getMethodCount());
        }
        sootClass.setApplicationClass();
        for (SootMethod m : sootClass.getMethods()) {

            try {
                new Transform("jtp.GotoInstrumenter", GotoInstrumenter.v())
                        .apply(m.retrieveActiveBody());
            } catch (Exception e) {
                System.out.println("Exeception in for loop : " + e);
            }
        }

    }

}

@SuppressWarnings("all")
class GotoInstrumenter extends BodyTransformer {
    private static GotoInstrumenter instance = new GotoInstrumenter();

    private GotoInstrumenter() {
    }

    public static GotoInstrumenter v() {
        return instance;
    }

    protected void internalTransform(Body body, String phaseName, Map options) {

        System.out.println("Processing method : "
                + body.getMethod().getSignature());
        Iterator<Unit> stmts = body.getUnits().iterator();
        HashStatement hs = new HashStatement();
        while (stmts.hasNext()) {
        	Unit stmt = stmts.next();
        	if (stmt.getClass() == JAssignStmt.class) {
        		AbstractDefinitionStmt astmt = (AbstractDefinitionStmt)stmt;
        		String output = "";
        		output += stmt;
        		output += " ---> [Entry] " + hs.toString();
        		Value lhs = astmt.getLeftOp();
        		Value rhs = astmt.getRightOp();
        		hs.put(lhs.toString(), rhs.toString());
        		output += " ---> [Exit] " + hs.toString();
        		System.out.println(output);
        	}
        }
        System.out.println("\n");
        
 
    }
}