# Parser Grammar

|Token|Term|rightTermn|
|-----|----|----------|
|START|was ist das fuer 1 code?|VSTART|
|VSTART|i bims 1|TYPE|
|TYPE|zal\|word\|isso|NAME|
|NAME|value|ASSI\|PSTART\|PNAME\|PEND|
|ASSI|goenn dir|CONST_ZAL\|CONST_ISSO\|CONST_WORD|
|CONST_ZAL|value|VEND\|PNEXT\|PEND|
|CONST_ISSO|value|VEND\|PNEXT\|PEND|
|CONST_WORD|value|VEND\|PNEXT\|PEND|
|VEND|!!!|VSTART\|CMD|
|CMD|was ist das fuer 1|NAME|
|PSTART|vong|CONST_ZAL\|CONST_ISSO\|CONST_WORD\|NAME\|PEND|
|PNEXT|,|CONST_ZAL\|CONST_ISSO\|CONST_WORD|
|PEND|her?|CMD\|END
|END|1 nicer!!!||