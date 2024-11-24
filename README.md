AddingUserNulls
This branch is dedicated to adding null functionality for users using dummy objects.

Method
Cast the sub array back to a purely object array allowing the insertion of a "dummy" object. This will function as a userNull. Essentially allowing me to treat nulls as my own type for a "truely" empty cell.

New function nullInsert (Will take an index and change whatever is there to a null)

Function to be considered:
IMPORTANT: How to deal with userNulls when sorting (Ignore or add to end or beginning) isEmpty and length will include user nulls even if they are at either end ADD: removeNulls(Removes all userNulls from the array)

Functions that do insert
append (Uses insert as well as addToEnd)
addToEnd (Will this allow nulls?)
push (This uses append I believe so should be ok)
replace (This directly blocks nulls. Needs change)
insert (This uses an external BinarySearch module check over null support)
appendAll (This uses append I believe so should be ok)
enqueue (This uses append I beleieve so should be ok)
Functions that do not insert
remove (Should be fine as it just sets an index to null, check however)
pop (Should be fine as nothing fancy goes on)
dequeue (Should be fine as nothing fancy goes on)
contains (should search for user nulls)
firstIndexOf (Should search for user nulls)
allIndexesOf (Should search for all user nulls)
