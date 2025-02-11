package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board;
    TeamColor turn;

    public ChessGame() {
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        HashSet<ChessMove> posMoves = (HashSet<ChessMove>) piece.pieceMoves(board, startPosition);
        HashSet<ChessMove> validMoves = HashSet.newHashSet(posMoves.size());
        for(ChessMove move : posMoves)


        return collection;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Find the King
        ChessPosition kingPos = null;
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++) {
                if(board.getPiece(new ChessPosition(i, j)).equals(new ChessPiece(teamColor, ChessPiece.PieceType.KING))){
                    kingPos = new ChessPosition(i,j);
                }
            }
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++) {
                if(board.getTeam(new ChessPosition(i, j)) != teamColor){
                    Collection<ChessMove> eMoves = validMoves(new ChessPosition(i, j));
                    for(ChessMove move : eMoves){
                        if(move.getEndPosition() == kingPos){return true;}
                    }
                }
            }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++) {
                if(board.getTeam(new ChessPosition(i,j)) == teamColor){
                    if (validMoves(new ChessPosition(i,j)) != null){
                        return false;
                    }
                }
            }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        board.resetBoard();
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
