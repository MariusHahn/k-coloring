package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph

enum class Algorithms(val alg : GraphColoring) {
    GREEDY_SHUFFLE(Greedy(Graph.NodeOrdering.SHUFFLE)),
    GREEDY_DESCENDING(Greedy(Graph.NodeOrdering.MOST_EDGES_FIRST)),
    GREEDY_ASCENDING(Greedy(Graph.NodeOrdering.LEAST_EDGES_FIRST)),
    BACKTRACKING_4(Backtracking(4, false)),
    BACKTRACKING_4_PARALLEL(Backtracking(4, true)),
    BACKTRACKING_5(Backtracking(5, false)),
    BACKTRACKING_5_PARALLEL(Backtracking(5, true)),
    BACKTRACKING_6(Backtracking(6, false)),
    BACKTRACKING_6_PARALLEL(Backtracking(6, true)),
    WELSH_POWELL_SHUFFLE_NODES(WelshPowell(Graph.NodeOrdering.SHUFFLE)),
    WELSH_POWELL_LEAST_EDGES_FIRST(WelshPowell(Graph.NodeOrdering.LEAST_EDGES_FIRST)),
    WELSH_POWELL_MOST_EDGES_FIRST(WelshPowell(Graph.NodeOrdering.MOST_EDGES_FIRST)),
    K4_COLORING(KColoring(4)),
    K5_COLORING(KColoring(5)),
    K6_COLORING(KColoring(6)),
}