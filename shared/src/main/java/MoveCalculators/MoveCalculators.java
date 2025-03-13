package MoveCalculators;

import chess.ChessBoard;
import chess.ChessPosition;
import chess.ChessGame;
import chess.ChessMove;

import java.util.Collection;
import java.util.HashSet;

public class MoveCalculators {


    public static boolean canMove(ChessPosition position){
        return (position.getRow() <= 8 && position.getRow() >= 1)
                && (position.getColumn() <= 8 && position.getColumn() >= 1);
    }

    protected static Collection<ChessMove> generateMoves(ChessBoard board, ChessPosition myPosition, int[][] posMoves) {
        HashSet<ChessMove> moves = HashSet.newHashSet(8);
        int curRow = myPosition.getRow();
        int curCol = myPosition.getColumn();
        ChessGame.TeamColor myTeam = board.getTeam(myPosition);
        for (int[] posMove : posMoves) {
            ChessPosition newPos = new ChessPosition(curRow + posMove[0], curCol + posMove[1]);
            if (MoveCalculators.canMove(newPos) && myTeam != board.getTeam(newPos))
                    moves.add(new ChessMove(myPosition,newPos,null));
        }
        return moves;
    }
    protected static Collection<ChessMove> generateDiagMoves(ChessBoard board, ChessPosition myPosition, int[][] posMoves) {
        HashSet<ChessMove> moves = HashSet.newHashSet(27);
        int curRow = myPosition.getRow();
        int curCol = myPosition.getColumn();
        ChessGame.TeamColor myTeam = board.getTeam(myPosition);
        for (int[] posMove : posMoves) {
            boolean blocked = false;
            int i = 1;
            while (!blocked) {
                ChessPosition newPos = new ChessPosition(curRow + posMove[0]*i, curCol + posMove[1]*i);
                if (!canMove(newPos)){
                    blocked = true;
                }
                else if (board.getPiece(newPos) == null) {
                    moves.add(new ChessMove(myPosition, newPos, null));
                }
                else if (board.getTeam(newPos) != myTeam){
                    moves.add(new ChessMove(myPosition, newPos, null));
                    blocked = true;
                }
                else if (board.getTeam(newPos) == myTeam){
                    blocked = true;
                }
                else {
                    blocked = true;
                }
                ++i;
            }

        }
        return moves;
    }
}
