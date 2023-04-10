-------------------------- MODULE assignment1specs --------------------------

EXTENDS TLC, Sequences, Integers

NumThreads == 5
Threads == 1..NumThreads

(* --algorithm threads

variables 
  counter = 0;

define
  AllDone == 
    \A t \in Threads: pc[t] = "Done"

  Correct ==
      AllDone => counter = NumThreads
end define;  

process thread \in Threads
variables tmp = 0;
begin
  GetCounter:
    tmp := counter;

  IncCounter:
    counter := tmp + 1;
end process;
end algorithm; *)
\* BEGIN TRANSLATION (chksum(pcal) = "98416dfd" /\ chksum(tla) = "eac8ba3")
VARIABLES counter, pc

(* define statement *)
AllDone ==
  \A t \in Threads: pc[t] = "Done"

Correct ==
    AllDone => counter = NumThreads


vars == << counter, pc >>

ProcSet == (Threads)

Init == (* Global variables *)
        /\ counter = 0
        /\ pc = [self \in ProcSet |-> "IncCounter"]

IncCounter(self) == /\ pc[self] = "IncCounter"
                    /\ counter' = counter + 1
                    /\ pc' = [pc EXCEPT ![self] = "Done"]

thread(self) == IncCounter(self)

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == (\E self \in Threads: thread(self))
           \/ Terminating

Spec == Init /\ [][Next]_vars

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION 
====
\* Modification History
\* Last modified Sun Apr 09 19:22:50 CEST 2023 by Agno
\* Created Sun Apr 09 17:48:58 CEST 2023 by Agno
