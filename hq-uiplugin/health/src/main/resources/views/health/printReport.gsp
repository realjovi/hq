<div id="reportHeader">
  <pre>
-- HQ Health Report --
${reportTime}
${userName} @ ${fqdn}

${dbVersion}

-- HQ Load --
${l.metricsPerMinute}: ${metricsPerMinute}
${l.numPlatforms}: ${numPlatforms}
${l.numCpus}: ${numCpus}
${l.numAgents}: ${numAgents}
${l.numActiveAgents}: ${numActiveAgents}
${l.numServers}: ${numServers}
${l.numServices}: ${numServices}
${l.numApplications}: ${numApplications}
${l.numRoles}: ${numRoles}
${l.numUsers}: ${numUsers}
${l.numAlertDefs}: ${numAlertDefs}
${l.numResources}: ${numResources}
${l.numResourceTypes}: ${numResourceTypes}
${l.numGroups}: ${numGroups}
${l.numEscalations}: ${numEscalations}
${l.numActiveEscalations}: ${numActiveEscalations}

-- System Vitals --
System Load Average:
- ${l.oneMin}: ${loadAvg1}
- ${l.fiveMin}: ${loadAvg5}
- ${l.fifteenMin}: ${loadAvg15}
    
System Processors: <% for (c in cpuInfos) { %>
- ${c.vendor} ${c.model} ${c.mhz} <% } %>

System Memory:
- ${l.total}: ${totalMem}
- ${l.used}: ${usedMem}
- ${l.free}: ${freeMem}
    
System Swap:
- ${l.total}: ${totalSwap}
- ${l.used}: ${usedSwap}
- ${l.free}: ${freeSwap}
    

-- HQ Process --
- Version: ${hqVersion}
- Schema: ${schemaVersion}
- ID: ${guid}
- Command Line: ${cmdLine}
- ${l.pid}: ${pid}
- ${l.procOpenFds}: ${procOpenFds}
- ${l.procStartTime}: ${procStartTime}
- ${l.procMemSize}: ${procMemSize}
- ${l.procMemRes}: ${procMemRes}
- ${l.procMemShare}: ${procMemShare}
- ${l.procCpu}: ${procCpu}
- ${l.numCpu}: ${numCpu}

Environment:
<% for (p in procEnv.entrySet().sort {a,b->a.key <=> b.key}) { %>
- ${p.key} = ${p.value} <% } %>


-- ${l.jvm} --
- ${l.jvmPercMem}: ${jvmPercMem}
- ${l.jvmFreeMem}: ${jvmFreeMem}
- ${l.jvmTotalMem}: ${jvmTotalMem}
- ${l.jvmMaxMem}: ${jvmMaxMem}

    
-- JVM Properties --
<% for (p in jvmProps.entrySet().sort {a,b->a.key <=> b.key}) { %>
- ${p.key} = ${p.value} <% } %>


<% for (d in diagnostics) { %>
-- ${l.diagnostics}:  ${d.name} --

${d.status}


<% } %>


<% if (licenseInfo) { %>
-- License --
Licensee:  ${licenseInfo.licensee}
Expire:    ${licenseInfo.licenseExpire}
Platforms: ${licenseInfo.licensePlatforms}
Count:     ${licenseInfo.platformCount}
<% } %>



-- Agents --
<%= agentFmt.sprintf([l.fqdn, AgentSortField.ADDR.value, 
                      AgentSortField.PORT.value, AgentSortField.VERSION.value,
                      AgentSortField.CTIME.value, l.numPlatforms, 
                      l.timeOffset, l.numMetrics, l.licenseCount] as Object[]) %>
-----------------------------------------------------------------------                      
<% for (a in agentData) { %>
<%= agentFmt.sprintf([a.platform, a.server, "${a.agent.port}", a.agent.version, 
                      a.creationTime, "${a.agent.platforms.size()}", 
                      "${a.offset}", "${a.numMetrics}", a.licenseCount] as Object[]) %> <% } %>
                      



<% if (jvmSupportsTraces) { %>
-- Thread Dump --
  <% for (ent in Thread.allStackTraces) { %>
    <%= h(ent.key) %>
    <% for (elem in ent.value) { %> <%= h(elem) %> 
    <% } %>
  <% } %>
<% } %>
    
  </pre>
</div>