# Encryption Project

> By John Medeiros and Darryl Wang for Prof. Polak's CSC 172

## Authorship

Authorship is often shared. [Find this project on github](https://github.com/Tridwoxi/Encryption) for blame.

| File or Task       | Primary author   | Contains                   |
| ------------------ | ---------------- | -------------------------- |
| RoundFunction.java | John             | everything you need for the round function: overall architecture, f function, permute, substitute, xor | 
| KeyScheduler.java  | Darryl           | keyscheduler to produce and distribute keys | 
| Encryption.java    | Darryl           | the things not related to encryption: user input, file IO, and various preprocessing | 
| Documentation and Revision | Shared   | a hope that the project works fine | 

## Implementation details

This project's naming scheme and implementation details deviate slightly from the specification, but the functionality is the same.

| Required method   | Where in this project?                        |
| ----------------- | --------------------------------------------- |
| `xorIt`           | "exclusive_or" in RoundFunction.java          |
|  `shiftIt`        | "rotate" in RoundFunction.java                | 
| `permuteIt`       | "permute" in RoundFunction.java               | 
| `SubstitutionS`   | "substitute" in RoundFunction.java            | 
| `functionF`       | "f_function" in RoundFunction.java            | 
| `encryptBlock`    | "roundfunctions" in RoundFunction.java, when given an additional parameter, will encrypt contents. it accepts a key instead of a queue of subkeys, but creates a KeyScheduler that contains a queue to pull subkeys from | 
| `decryptBlock`    | same as encryptBlock, but the additional parameter causes decryption by pulling from a stack of subkeys so they're in reverse order | 
| `encryption`      | part of the main method in Encryption.java    | 
| `decryption`      | part of the main method in Encryption.java    | 
| `keyScheduleTransform`| appears as the class "KeyScheduler" in its own file | 
| `runTests`        | "run_tests" in Encryption.java                |

