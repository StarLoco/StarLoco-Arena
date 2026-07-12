package service

import (
	"context"
	"errors"
	"fmt"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// SocialService handles friend/ignore-list management. Chat delivery
// (private/vicinity messages) is handled by the world/dispatch layer
// directly against the online registry, not here, since it doesn't touch
// persistence.
type SocialService struct {
	db *gorm.DB
}

// NewSocialService constructs a SocialService bound to db.
func NewSocialService(db *gorm.DB) *SocialService {
	return &SocialService{db: db}
}

// AddFriend records a friendship edge from ownerID to friendID. Returns
// false if the edge already existed (no-op).
func (s *SocialService) AddFriend(ctx context.Context, ownerID, friendID uint) (bool, error) {
	return s.addEdge(ctx, &domain.CoachFriend{OwnerID: ownerID, FriendID: friendID}, "owner_id = ? AND friend_id = ?", ownerID, friendID)
}

// RemoveFriend deletes a friendship edge.
func (s *SocialService) RemoveFriend(ctx context.Context, ownerID, friendID uint) error {
	return s.db.WithContext(ctx).Where("owner_id = ? AND friend_id = ?", ownerID, friendID).Delete(&domain.CoachFriend{}).Error
}

// AddIgnored records an ignore edge from ownerID to ignoredID.
func (s *SocialService) AddIgnored(ctx context.Context, ownerID, ignoredID uint) (bool, error) {
	return s.addEdge(ctx, &domain.CoachIgnored{OwnerID: ownerID, IgnoredID: ignoredID}, "owner_id = ? AND ignored_id = ?", ownerID, ignoredID)
}

// RemoveIgnored deletes an ignore edge.
func (s *SocialService) RemoveIgnored(ctx context.Context, ownerID, ignoredID uint) error {
	return s.db.WithContext(ctx).Where("owner_id = ? AND ignored_id = ?", ownerID, ignoredID).Delete(&domain.CoachIgnored{}).Error
}

func (s *SocialService) addEdge(ctx context.Context, edge any, whereQuery string, args ...any) (bool, error) {
	var count int64
	table := s.db.Model(edge)
	if err := table.Where(whereQuery, args...).Count(&count).Error; err != nil {
		return false, fmt.Errorf("service: check existing edge: %w", err)
	}
	if count > 0 {
		return false, nil
	}
	if err := s.db.WithContext(ctx).Create(edge).Error; err != nil {
		if errors.Is(err, gorm.ErrDuplicatedKey) {
			return false, nil
		}
		return false, fmt.Errorf("service: create edge: %w", err)
	}
	return true, nil
}
