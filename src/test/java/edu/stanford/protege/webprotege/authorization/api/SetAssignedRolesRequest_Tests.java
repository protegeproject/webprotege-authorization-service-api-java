package edu.stanford.protege.webprotege.authorization.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SetAssignedRolesRequest_Tests {

    private SetAssignedRolesRequest request;

    @Autowired
    private JacksonTester<SetAssignedRolesRequest> tester;

    @Autowired
    private Subject subject;

    @Autowired
    private Resource resource;

    @Autowired
    private Set<RoleId> roleIds;

    @BeforeEach
    void setUp() {
        request = new SetAssignedRolesRequest(subject,
                                              resource,
                                              roleIds);
    }

    @Test
    void shouldSupplyChannelName() {
        assertThat(request.getChannel()).isEqualTo("authorization.SetAssignedRoles");
    }

    @Test
    void shouldSerializeRequest() throws IOException {
        var json = tester.write(request);
        assertThat(json).hasJsonPath("subject");
        assertThat(json).hasJsonPath("resource");
        assertThat(json).hasJsonPath("roles");
    }

    @Test
    void shouldDeserializeRequest() throws IOException {
        var json = """
                {
                    "subject": {
                        "@type" : "User",
                        "userId" : "John Smith"
                    },
                    "resource": {
                        "@type" : "Project",
                        "projectId" : "00000000-0000-0000-0000-000000000000"
                    },
                    "roles" : [
                        "ProjectEditor"
                    ]
                }
                """;
        var parsed = tester.parse(json).getObject();
        assertThat(parsed).isEqualTo(request);
    }
}