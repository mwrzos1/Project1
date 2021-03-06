package edu.towson.cosc.cosc455.mwrzos1.project1

class MyLexicalAnalyzer extends LexicalAnalyzer {

  //values declaration

  var nchar: Char = ' '
  var tokens: String = ""
  var position: Int = 0

  override def addChar(): Unit = {

    // add characters to token
    tokens += nchar


  }

  override def getChar(): Unit = {

    // get the character
   if (position < Compiler.length) {
     nchar = Compiler.fileContents.charAt(position)

     // increase position by 1 each time you read character
     position += 1

   }}

  // to reset a token function
  def resetToken(): Unit = {
    tokens = ""
  }

  override def getNextToken(): Unit = {
    resetToken()
    getChar()
    nonSpace()


      if (CONSTANTS.specialChars.contains(nchar)) {
        if (CONSTANTS.BOLD.contains(nchar)) {
          addChar()
          getChar()
        }
        else if (CONSTANTS.ADDRESSE.contains(nchar)) {
          addChar()
          getChar()
        }
        else if (CONSTANTS.LISTITEM.contains(nchar)) {
          addChar()
          tokens += read()
        }
        else if (CONSTANTS.specialChars(4) == (nchar)) {
          addChar()
          tokens += read()
          if (CONSTANTS.NEWLINE.charAt(1) == (nchar)) {
            addChar()
            Compiler.currentToken = tokens
            position += 1
            return
          }
          if (CONSTANTS.leftBracket == (nchar)) {
            addChar()
            Compiler.currentToken = tokens
            return
          }
          if (CONSTANTS.BRACKETE.contains(nchar)) {
            addChar()
          }
          if (CONSTANTS.DOCE.contains(tokens.toUpperCase())) {
            nonSpace()
            if (position - Compiler.length != 0) {
              position = position - 1
              getNextToken()
              println("It shouldn't be anything after document ends")
              System.exit(1)
            }
          }
        }
        else if (CONSTANTS.HEADING.contains(nchar)) {
          addChar()
          tokens += read()
        }
        else if (CONSTANTS.IMAGEB.charAt(0) == nchar) {
          addChar()
          getChar()
          if (CONSTANTS.IMAGEB.charAt(1) == nchar) {
            addChar()
            if (lookup()) Compiler.currentToken = tokens
          }
        }
        else if (CONSTANTS.specialChars.contains(nchar))
          {
            addChar()
          }
        if (tokens.endsWith("\n") || tokens.endsWith("\t")) {
          tokens = tokens.substring(0, tokens.length - 1)
        }
        if (lookup()) {
          Compiler.currentToken = tokens
          return
        }
        else {
          println("Lexical error, invalid token received: " + tokens)
          System.exit(1)
        }
      }
      else if (nchar.isLetterOrDigit || nchar.equals(':') || nchar.equals('.') || nchar.equals(',')) {
        addChar()
        tokens += read()
        if (nchar.toString == CONSTANTS.BRACKETE || nchar.toString == CONSTANTS.PARAE || nchar.toString == CONSTANTS.EQSIGN || nchar.equals('\\') || nchar.toString == CONSTANTS.ADDRESSE) {
          position -= 1
        }
        Compiler.currentToken = tokens
      }
    }

    def lookup(): Boolean = {
      // if the tokens legal return true if not false
      if (CONSTANTS.Constants.contains(tokens.toUpperCase())) return true
      else return false
    }

    //function that reads the text until the end of word, token or line
    def read(): String = {
      var text: String = ""
      getChar()

      while (position < Compiler.length && !CONSTANTS.EOFL.contains(nchar) && !CONSTANTS.specialChars.contains(nchar)) {
        text += nchar

        getChar()
      }
      nchar match {
        case ('\n') => text += nchar
        case ('\r') => getChar(); if (nchar == '\t') text += nchar
        case _ => {}
      }
      return text
    }
    //function call until no space found
    def nonSpace(): Unit = {

      while ((nchar.equals(' ') || nchar.equals('\n') || nchar.equals('\r') || nchar.equals('\t')) && (position < Compiler.length) ) {
        getChar()
      }
    }
}