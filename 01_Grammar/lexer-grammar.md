# Lexer Grammar

|Token|Regular Expression|
|-----|------------------|
|START|(was ist das fuer 1 code\\?).*|
|END|(1 nicer!!!).*|
|CMD|(was ist das fuer 1).*|
|PSTART|(vong).*|
|PNEXT|(,).*|
|PEND|(her\\?).*|
|PRINT|(gieb).*|
|AAL|(halo i bims!!!).*|
|IFSTART|(bist du).*|
|IFEND|(real rap).*|
|VSTART|(i bims 1).*|
|ASSI|(goenn dir).*|
|VEND|(!!!).*|
|WHITESPACE|( ).*|
|NEWLINE|(\n).*|
|COMMENT|(:X).*|
|TYPE|(zal\|word\|isso).*|
|CONST_ZAL|\\b(\\d{1,9})\\b.*|
|CONST_ISSO|\\b(yup\|nope)\\b.*|
|CONST_WORD|\\\"(.*?)\\\".*|
|NAME|\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*|