package fiit.restlistener.model;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import tech.sirwellington.alchemy.http.AlchemyHttp;
import tech.sirwellington.alchemy.http.exceptions.AlchemyHttpException;

public class TopologyAccess {

	private static final String HTTP_TAG = "http://";
	private static final String SWITCHES_URL = "/v1.0/topology/switches";
	private static final String HOSTS_URL = "/v1.0/topology/hosts";
	private static final String LINKS_URL = "/v1.0/topology/links";
	private static final Comparator<ModelHost> HOST_COMPARATOR = ((h1,h2) -> {
		Integer o1Dpid = h1.getLinkedSwitchPort().getDpid();
		Integer o2Dpid = h2.getLinkedSwitchPort().getDpid();
		Integer o1PortNo = h1.getLinkedSwitchPort().getPortNo();
		Integer o2PortNo = h2.getLinkedSwitchPort().getPortNo();
		if (o1Dpid > o2Dpid) {
			return 1;
		} else if (o1Dpid == o2Dpid) {
			if (o1PortNo > o2PortNo) {
				return 1;
			} else if (o1PortNo == o2PortNo) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	});
	
	private final AlchemyHttp http = AlchemyHttp.newDefaultInstance();
	private String ipv4AndPort;
	
	public TopologyAccess(String ipv4AndPort) {
		setIpv4AndPort(ipv4AndPort);
	}
	
	private void setIpv4AndPort(String ipv4AndPort) {
		this.ipv4AndPort = ipv4AndPort;
	}
	
	public LinkedHashSet<ModelSwitch> getSwitches() throws AlchemyHttpException, MalformedURLException, 
			IllegalArgumentException {
		LinkedHashSet<ModelSwitch> switches = new LinkedHashSet<>(Arrays.asList(http.go()
			.get()
			.expecting(ModelSwitch[].class)
			.at(HTTP_TAG + ipv4AndPort + SWITCHES_URL)));
		return switches;
	}
	
	public LinkedHashSet<ModelHost> getHosts() throws AlchemyHttpException, MalformedURLException, 
			IllegalArgumentException {
		LinkedHashSet<ModelHost> hosts = new LinkedHashSet<>(Arrays.asList(http.go()
			.get()
			.expecting(ModelHost[].class)
			.at(HTTP_TAG + ipv4AndPort + HOSTS_URL)));
		List<ModelHost> hostsSorted = new ArrayList<ModelHost>(hosts);
		hostsSorted.sort(HOST_COMPARATOR);
		AtomicInteger i = new AtomicInteger(1);
		hostsSorted.forEach(h -> h.setHostName("h" + i.getAndIncrement()));
		return hosts;
	}
	
	public LinkedHashSet<ModelLink> getLinks() throws AlchemyHttpException, MalformedURLException, 
			IllegalArgumentException {
		LinkedHashSet<ModelLink> links = new LinkedHashSet<>(Arrays.asList(http.go()
			.get()
			.expecting(ModelLink[].class)
			.at(HTTP_TAG + ipv4AndPort + LINKS_URL)));
		return links;
	}
	
	public List<Edge> getEdges(LinkedHashSet<ModelLink> links, LinkedHashSet<ModelSwitch> switches, 
			LinkedHashSet<ModelHost> hosts) {
		List<Edge> edges = hosts.stream()
				.map(h -> h.getEdge(switches))
				.filter(p -> p!=null)
				.collect(Collectors.toCollection(ArrayList::new));
		List<Edge> edgesHosts = links.stream()
				.map(h -> h.getEdge(switches))
				.filter(p -> p!=null)
				.collect(Collectors.toCollection(ArrayList::new));
		edges.addAll(edgesHosts);
		return edges;
	}
	
}
