package chess;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

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
        board = new ChessBoard();
        board.resetBoard();
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return turn;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", turn=" + turn +
                '}';
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        turn = team;
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
        if(piece == null){return null;}
        Collection<ChessMove> posMoves = board.getPiece(startPosition).pieceMoves(board,startPosition);
        if(posMoves == null){posMoves = Collections.emptyList();}
        HashSet<ChessMove> chessPosMoves = new HashSet<>(posMoves);
        HashSet<ChessMove> validMoves = new HashSet<>(posMoves.size());
        for(ChessMove move : chessPosMoves){
            ChessPiece temp = board.getPiece(move.getEndPosition());
            board.addPiece(startPosition,null);
            board.addPiece(move.getEndPosition(),piece);
            if(!isInCheck(piece.getTeamColor())){validMoves.add(move);}
            board.addPiece(move.getEndPosition(),temp);
            board.addPiece(startPosition, piece);
        }


        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isTeamTurn = (getTeamTurn() == board.getTeam(move.getStartPosition()));
        Collection<ChessMove> potMoves = validMoves(move.getStartPosition());
        if(potMoves == null){
            throw new InvalidMoveException();
        }
        boolean isValidMove = potMoves.contains(move);
        if(isValidMove && isTeamTurn){
            ChessPiece piece = board.getPiece(move.getStartPosition());
            if (move.getPromotionPiece() != null){
                piece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
            }
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), piece);
            setTeamTurn(getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);

        }
        else{
            throw new InvalidMoveException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
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
        for(int i = 1; i <= 8 && kingPos == null; i++) {
            for (int j = 1; j <= 8 && kingPos == null; j++) {
                ChessPiece currPiece = board.getPiece(new ChessPosition(i, j));
                if (currPiece == null) {
                    continue;
                }
                if (currPiece.getTeamColor() == teamColor && currPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPos = new ChessPosition(i, j);
                }

            }
        }
        for(int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece currPiece = board.getPiece(new ChessPosition(i, j));
                if (currPiece == null || currPiece.getTeamColor() == teamColor) {
                    continue;
                }
                Collection<ChessMove> eMoves = currPiece.pieceMoves(board, new ChessPosition(i, j));
                if (eMoves != null){
                    for (ChessMove move : eMoves) {
                        if (move.getEndPosition().equals(kingPos)) {
                            return true;
                        }
                    }
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
        return isInCheck(teamColor) && !isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(isInCheck(teamColor)){
            return false;
        }
        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++) {
                ChessPosition curPos = new ChessPosition(i,j);
                ChessPiece curPiece = board.getPiece(curPos);
                Collection<ChessMove> moves;
                if(curPiece != null && teamColor == curPiece.getTeamColor()){
                    moves = validMoves(curPos);
                    if(moves != null && !moves.isEmpty()){
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
