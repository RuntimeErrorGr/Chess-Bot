## Team: Oxfff

- **Irina Botici**  
- **Cristina Elena Hudisteanu**  
- **Andrei Liviu Labau**

---

## Stage 1

In this stage, we implemented the following functionalities for a pawn:

- Jump to a square on the board
- Double jump (only allowed on its first move)
- Diagonal capture of another piece

We also implemented support for the following commands: `feature`, `go`, `force`, and `move`.

### Implementation Details

For this stage, we hardcoded the position of the pawn we work with — the leftmost one (starting at `a7` for black and `a2` for white).  
If the pawn runs out of valid moves or is promoted to a queen, we send a `resign` command.

---

### `Parser` Class

Responsible for parsing input from the `xboard` interface.

**Attributes:**
- `enginesColor`: the color assigned to the engine
- `noMoves`: a counter to track the number of moves in Edit mode
- `Scanner`: reads from standard input
- `chessBoard`: instance of the chess board
- `isForced`: flag to indicate if we're in Edit mode
- `chessPiece`: the chess piece being controlled

**Methods:**
- `parse()`: Continuously reads input and delegates to the appropriate command method.
- `feature()`: Sends configuration parameters (`sigint`, `san`, `name`) to `xboard`.
- `New()`: Resets internal parameters, including board state.
- `go()`: Determines which color is to move, checks if the pawn still exists, and acts accordingly.
- `move()`: Handles logic for the move command; checks if it's the engine's turn, updates board accordingly.
- `resign()`: Sends a resign command to `xboard`.
- `checkIfQueen()`: Checks if the pawn has reached promotion and resigns if so.

---

### `ChessBoard` Class

Singleton class representing the internal chess board.

**Attributes:**
- `Tile[][] matrix`: 8x8 matrix of `Tile` objects

**Methods:**
- `findTileOnBoard(String coordinates)`: Finds a `Tile` based on coordinates.
- `findPieceOnBoard(String coordinates)`: Locates a `ChessPiece` at a given starting coordinate.
- `initializeBoard()`: Initializes the tile matrix and places all pieces in starting positions.
- `placePiecesOnBoard()`: Places kings, queens, bishops, knights, rooks, and pawns using a `PieceFactory`.
- `updateMatrix(String coordinates)`: Updates board state after a move, checks for captures, and promotes if needed.
- `resetBoard()`: Reinitializes the matrix.

---

### `Tile` Class

Represents a square on the board.

**Attributes:**
- `color`: true for white, false for black
- `isOccupied`: whether a piece is on the tile
- `chessPiece`: the piece occupying this tile (null if empty)
- `coordinates`: fixed coordinates

---

### `ChessPiece` Class

Abstract class for all chess pieces.

**Attributes:**
- `color`: piece color
- `pieceType`: e.g., `"pawn"`, `"rook"`, `"queen"`, etc.
- `currCoordinates`: current location
- `startCoordinates`: initial location (used as unique ID)

---

### `Pawn` Class

Extends `ChessPiece` and represents a pawn.

**Attributes:**
- `firstMove`: indicates if this is the pawn's first move

**Methods:**
- `move()`: Handles movement logic and resigns if no valid move exists.
- `computeMove()`: Determines the best next move and returns it as a string.
- `moveBlack(char letter, char number)`: Computes move options for a black pawn.
- `moveWhite(char letter, char number)`: Same logic as `moveBlack()` but for white.

---

### Remaining Pieces

To be implemented in future stages:
- `Bishop`
- `Knight`
- `King`
- `Queen`
- `Rook`

---

### Stage 1 Contributions

- **Labau**: Implemented `Board` & `Piece` packages, debugging  
- **Hudisteanu**: Implemented `Board` & `Piece` packages, debugging  
- **Botici**: Implemented `Parser`, `Makefile`, Javadoc, debugging  

---

## Stage 2

### Key Functionalities Added

1. Legal movement for **all** pieces
2. **En passant**
3. **Kingside castling**
4. **Queenside castling**
5. **Check awareness**
6. Defense against **simple check**
7. Defense against **discovered check**
8. **Blocking** moves that expose the king
9. **Pawn promotion**

> All features are implemented for both white and black engines.

---

### General Approach

- Used the codebase from Stage 1 as a base.
- Each piece has its own `move()` and `computeMove()` methods.
- At game start, all white and black pieces are stored in lists.
- Each turn, a piece is chosen randomly and checked for legal moves:
  - If in check → prioritize king movement
  - Then → prioritize castling
  - Otherwise → pick a legal move that does not expose the king
- If no legal move is found, the bot resigns.
- `Parser.parse()` orchestrates the game loop and decision logic.

---

### Important Methods in Each Piece

- `computeEligiblePositions()`: Generates a list of all legal moves for a piece
- `givesChess()`: Checks if the opponent is putting our king in check
- `defending()`: Determines if a piece is defending the king and restricts its movement accordingly

---

### En Passant

- Attributes: `enPassR`, `enPassL` track right/left capture eligibility
- Checked after every pawn move to determine if en passant is possible
- Eligibility is lost if the capturing move isn't made in the next round

---

### Castling

- **Kingside Castling**
  - Checked using `checkKingSideCastling()` and `isGuarded()` (from `Tile`)
  - Ensures king and rook haven't moved, no pieces in between, and squares are not under attack

- **Queenside Castling**
  - Same as kingside but also requires the queen’s square to be empty

---

### Check Detection & Defense

- Detected via `givesChess()` after every move
- Bot will:
  - Move the king to a safe square
  - Capture the attacking piece if possible
  - Block the attack if necessary

---

### Pawn Promotion

- Automatically triggers when a pawn reaches the last rank:
  - `g7g8q` → queen
  - `g7g8n` → knight
  - `g7g8b` → bishop
  - `g7g8r` → rook
- Internal board state is updated accordingly

---

### Stage 2 Contributions

- **Labau**: En passant, check defense, castling, debugging  
- **Hudisteanu**: Piece movement logic, check defense, debugging  
- **Botici**: Board structure, castling, en passant, `Makefile`, Javadoc, debugging  
