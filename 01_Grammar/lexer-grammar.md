# Lexer Grammar

|Token|Regular Expression|
|-----|------------------|
|START|(was ist das fuer 1 code?).*|
|END|(1 nicer!!!).*|
|CONST_ZAL|\\b(\\d{1,9})\\b.*|
|CONST_ISSO|\\b(yup\|nope)\\b.*|
|CONST_WORD|\\\"(.*?)\\\".*|
|CMD|(was ist das fuer 1).*|
|PSTART|(vong).*|
|PEND|(her?).*|
|ASSI|(goenn dir).*|
|WHITESPACE|( \|!!!).*|
|NEWLINE|(\n).*|
|VAR|(i bims 1).*|
|TYPE|(zal\|word\|isso).*|
|NAME|\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*|