// This test case should generate 12 page faults and display output as Sum=66.
main()
{
    int i;
    char  Sum=0;
    char * charPtr= (char *)0x0100000;
    for(i=0;i<12;i++)
          *(charPtr + 8196 *i)=(char )i;
    for(i=0;i<12;i++)
        Sum+= *(charPtr + 8196 *i);
    Printf("For some reason, putting this printf statement here makes it work...?\n");
    Printf("In Userprog1, Sum : %d\n",Sum);
}
