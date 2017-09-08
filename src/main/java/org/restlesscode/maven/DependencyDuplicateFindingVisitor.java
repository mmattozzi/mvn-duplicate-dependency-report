package org.restlesscode.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.traversal.DependencyNodeVisitor;

import java.util.*;

public class DependencyDuplicateFindingVisitor implements DependencyNodeVisitor {

    protected DependencyNode rootNode;
    protected DependencyPath currentPath;

    protected Map<Integer, String> states;

    // Artifact -> Versions -> List of paths for version
    protected Map<String, Map<String, List<DependencyPath>>> dependencies = new HashMap<String, Map<String, List<DependencyPath>>>();

    public DependencyDuplicateFindingVisitor(DependencyNode rootNode) {
        this.rootNode = rootNode;
        this.states = new HashMap<Integer, String>();
        this.states.put(0, "Included");
        this.states.put(1, "Omitted for duplicate");
        this.states.put(2, "Omitted for conflict");
        this.states.put(3, "Omitted for cycle");
        
        currentPath = new DependencyPath();
        currentPath.artifact = null;
    }
    
    public boolean visit(DependencyNode node) {
        DependencyPath depPath = new DependencyPath();
        depPath.artifact = node.getArtifact();
        depPath.state = node.getState();
        depPath.prev = currentPath;
        currentPath.next = depPath;
        currentPath = depPath;

        Artifact artifact = node.getArtifact();
        String artifactStr = artifact.getGroupId() + ":" + node.getArtifact().getArtifactId();
        if (! dependencies.containsKey(artifactStr)) {
            dependencies.put(artifactStr, new HashMap<String, List<DependencyPath>>());
        }
        if (! dependencies.get(artifactStr).containsKey(artifact.getVersion())) {
            dependencies.get(artifactStr).put(artifact.getVersion(), new ArrayList<DependencyPath>());
        }
        dependencies.get(artifactStr).get(artifact.getVersion()).add(new DependencyPath(currentPath));

        return true;
    }

    public boolean endVisit(DependencyNode node) {
        currentPath = currentPath.prev;

        return true;
    }

    public void findDuplicates() {
        for (Map.Entry<String, Map<String, List<DependencyPath>>> artifactEntry : dependencies.entrySet()) {
            if (artifactEntry.getValue().size() > 1) {
                System.out.println("Multiple versions detected for " + artifactEntry.getKey());
                for (Map.Entry<String, List<DependencyPath>> versionEntry : artifactEntry.getValue().entrySet()) {
                    int state = 1;

                    for (DependencyPath dp : versionEntry.getValue()) {
                        state *= dp.state;
                    }

                    System.out.println("  " + versionEntry.getKey() + " - " + (state == 0 ? "Included" : "Omitted"));
                }
                for (Map.Entry<String, List<DependencyPath>> versionEntry : artifactEntry.getValue().entrySet()) {
                    System.out.println("  Paths to " + versionEntry.getKey() + " dependencies:");
                    for (DependencyPath dp : versionEntry.getValue()) {
                        dp.print();
                    }
                }
                System.out.println();
            }
        }
    }

    public class DependencyPath {
        public Artifact artifact;
        public DependencyPath next = null;
        public DependencyPath prev = null;
        public int state = 0;
        
        public DependencyPath() { }

        public void print() {
            DependencyPath current = this;
            
            // First rewind to the beginning of the path
            while (current.prev.artifact != null) {
                current = current.prev;
            }

            String spaces = "    ";
            while (current != null) {
                String leadingStr = (current.next == null) ? "\\ " : "+ ";
                String endStr = (current.next == null) ? " " + states.get(current.state) : "";
                System.out.println(spaces + leadingStr + current.artifact + endStr);
                spaces += "  ";
                current = current.next;
            }
        }

        public DependencyPath(DependencyPath seed) {
            this.artifact = seed.artifact;
            this.state = seed.state;
            if (seed.prev != null) {
                DependencyPath prev = new DependencyPath(seed.prev);
                this.prev = prev;
                prev.next = this;
            }
        }
    }

}
